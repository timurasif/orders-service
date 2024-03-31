package com.example.demo.Controllers;

import com.example.demo.Configuration.Constants;
import com.example.demo.Models.dto.*;
import com.example.demo.Models.dto.CreateOrder.CreateOrderRequest;
import com.example.demo.Models.dto.CreateOrder.CreateOrderResponse;
import com.example.demo.Models.dto.GetOrder.GetOrderResponse;
import com.example.demo.Models.dto.TakeOrder.TakeOrderRequest;
import com.example.demo.Models.dto.TakeOrder.TakeOrderResponse;
import com.example.demo.Services.OrderService;
import com.example.demo.Validation.Validator;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/orders")
@Validated
@AllArgsConstructor
public class OrderController {

    OrderService orderService;
    Validator validator;

    @PostMapping()
    @Operation(summary = "Create new order.")
    public Response<Object> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = String.format("Validation error: %s", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new CreateOrderResponse(null, null, null, errorMsg));
        }
        if(!validator.hasValidCoordinates(createOrderRequest.getOrigin())){
            String errorMsg = String.format("Validation error: %s", "Invalid Origin");
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new CreateOrderResponse(null, null, null, errorMsg));
        }
        if(!validator.hasValidCoordinates(createOrderRequest.getDestination())){
            String errorMsg = String.format("Validation error: %s", "Invalid Destination");
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new CreateOrderResponse(null, null, null, errorMsg));
        }

        CreateOrderResponse createdOrder = orderService.createNewOrder(createOrderRequest);
        return new Response<>(Constants.HttpStatusCodes.SUCCESS, createdOrder);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Take order.")
    public Response takeOrder(@PathVariable("id") Integer id, @Valid @RequestBody TakeOrderRequest takeOrderRequest, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = String.format("Validation error: %s", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new TakeOrderResponse(null, errorMsg));
        }
        if(!validator.hasValidStatus(takeOrderRequest.getStatus())){
            String errorMsg = String.format("Invalid order status: %s", takeOrderRequest.getStatus());
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new TakeOrderResponse(null, errorMsg));
        }
        if(takeOrderRequest.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_UNASSIGNED)){
            String errorMsg = String.format("Order status not meaningful: %s", takeOrderRequest.getStatus());
            return new Response<>(Constants.HttpStatusCodes.FORBIDDEN, new TakeOrderResponse(null, errorMsg));
        }

        boolean status = orderService.takeOrder(id, takeOrderRequest.getStatus().toUpperCase());
        return status ? new Response(Constants.HttpStatusCodes.SUCCESS, new TakeOrderResponse("SUCCESS", null)) :
                new Response(Constants.HttpStatusCodes.CONFLICT, new TakeOrderResponse(null, "Order already taken."));
    }

    @GetMapping()
    public Response getOrders(@RequestParam(name = "page", defaultValue = "1") String page,
                            @RequestParam(name = "limit", defaultValue = "10") String limit) {
        try {
            int pageNum = Integer.parseInt(page);
            int limitNum = Integer.parseInt(limit);

            orderService.getOrders(pageNum, limitNum);
            return new Response(Constants.HttpStatusCodes.SUCCESS, new GetOrderResponse(null, "OKAY."));
        } catch (NumberFormatException e) {
            return new Response(Constants.HttpStatusCodes.BAD_REQUEST, new GetOrderResponse(null, "Page and Limit must be Integers."));
        }
    }

}
