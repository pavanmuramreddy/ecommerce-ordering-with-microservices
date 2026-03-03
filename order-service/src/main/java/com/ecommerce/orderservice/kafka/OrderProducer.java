
package com.ecommerce.orderservice.kafka;

import com.ecommerce.orderservice.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-created", json);
            System.out.println("Order event sent to kafka");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}