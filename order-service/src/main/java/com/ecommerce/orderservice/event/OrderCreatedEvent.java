package com.ecommerce.orderservice.event;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderCreatedEvent {
    private Long orderId;
    private Long productId;
    private Integer quantity;
}
