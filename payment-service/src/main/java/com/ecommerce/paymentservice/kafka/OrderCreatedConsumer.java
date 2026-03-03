package com.ecommerce.paymentservice.kafka;

//import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
//@RequiredArgsConstructor

public class OrderCreatedConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public OrderCreatedConsumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics="order-created", groupId="payment-group")
    public void consume(String message){
        System.out.println("Payment Service received order: " + message);
        boolean paymentSuccess = true;

        if (paymentSuccess) {
            kafkaTemplate.send("payment-success", message);
            System.out.println("Payment Successful -> sent to payment-success ");
        }else{
            kafkaTemplate.send("payment-failed", message);
            System.out.println("Payment failed -> sent to payment-failed ");
        }
    }
}
