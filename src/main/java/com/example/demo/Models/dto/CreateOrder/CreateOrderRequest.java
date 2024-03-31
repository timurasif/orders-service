package com.example.demo.Models.dto.CreateOrder;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @Size(min = 2, max = 2, message = "Origin must have exactly 2 elements")
    private String[] origin = new String[2];

    @Size(min = 2, max = 2, message = "Destination must have exactly 2 elements")
    private String[] destination = new String[2];
}
