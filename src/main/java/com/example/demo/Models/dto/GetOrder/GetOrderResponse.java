package com.example.demo.Models.dto.GetOrder;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderResponse {
    List<Order> orders;
    private String error;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Order{
        Integer id;
        private Integer distance;
        private String status;
    }
}
