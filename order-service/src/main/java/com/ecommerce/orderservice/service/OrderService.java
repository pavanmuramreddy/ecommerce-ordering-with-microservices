package com.ecommerce.orderservice.service;

import com.ecommerce.grpc.InventoryRequest;
import com.ecommerce.grpc.InventoryResponse;
import com.ecommerce.grpc.InventoryServiceGrpc;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.event.OrderCreatedEvent;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.kafka.OrderProducer;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    //private final KafkaTemplate<String, String> kafkaTemplate;

    @GrpcClient("product-service")
    private InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

//    public OrderService(OrderRepository orderRepository,
//                        KafkaTemplate<String, String> kafkaTemplate) {
//        this.orderRepository = orderRepository;
//        this.kafkaTemplate = kafkaTemplate;
//    }

    public Order createOrder(Long productId, Integer quantity) {

        // Call Product Service via gRPC
        InventoryResponse response = inventoryStub.checkAndReserve(
                InventoryRequest.newBuilder()
                        .setProductId(productId)
                        .setQuantity(quantity)
                        .build()
        );

        if (!response.getSuccess()) {
            throw new RuntimeException(response.getMessage());
        }

        //  Save Order
        Order order = Order.builder()
                .productId(productId)
                .quantity(quantity)
                .status("CREATED")
                .build();

        orderRepository.save(order);
        orderProducer.sendOrderCreatedEvent(
                OrderCreatedEvent.builder()
                        .orderId(order.getId())
                        .productId(order.getProductId())
                        .quantity(order.getQuantity())
                        .build()
        );

        // Publish Kafka Event
        //kafkaTemplate.send("order-topic", "ORDER_CREATED:" + order.getId());

        return order;
    }
}
