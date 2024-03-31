package com.example.demo.Models.dto.TakeOrder;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeOrderRequest {

    @NotBlank(message = "Order status can not be empty")
    @NotNull(message = "Order status can not be null")
    private String status;
}
