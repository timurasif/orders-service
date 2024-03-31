package com.example.demo.Models.dto.CreateOrder;

import com.example.demo.Models.Entities.OrderEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponse {
    Integer id;
    private Integer distance;
    private String status;

    public static CreateOrderResponse fromEntity(OrderEntity entity) {
        return CreateOrderResponse.builder()
                .id(entity.getId())
                .distance(entity.getDistance())
                .status(entity.getStatus())
                .build();
    }
}
