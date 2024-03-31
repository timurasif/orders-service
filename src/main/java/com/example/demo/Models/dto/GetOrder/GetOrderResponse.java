package com.example.demo.Models.dto.GetOrder;

import com.example.demo.Models.Entities.OrderEntity;
import com.example.demo.Models.dto.CreateOrder.CreateOrderResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderResponse {
    List<OrderEntity> orders;
}
