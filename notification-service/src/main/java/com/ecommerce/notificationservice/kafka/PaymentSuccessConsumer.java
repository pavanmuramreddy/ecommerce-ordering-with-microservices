package com.ecommerce.notificationservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class PaymentSuccessConsumer {
    @KafkaListener(topics="payment-success", groupId="notification-group")
    public void consume(String message){
        System.out.println("Notification Service received payment-success: " + message);
        System.out.println("Sending order confirmation email..");
    }
}
