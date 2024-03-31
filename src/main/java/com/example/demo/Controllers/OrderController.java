package com.example.demo.Controllers;

import com.example.demo.Configuration.Constants;
import com.example.demo.Models.Entities.OrderEntity;
import com.example.demo.Models.ErrorResponse;
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

import java.util.List;
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
            String errorMsg = String.format(Constants.VALIDATION_ERROR_MESSAGE, Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new ErrorResponse(errorMsg));
        }
        if(!validator.hasValidCoordinates(createOrderRequest.getOrigin())){
            String errorMsg = String.format(Constants.VALIDATION_ERROR_MESSAGE, Constants.INVALID_ORIGIN);
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new ErrorResponse(errorMsg));
        }
        if(!validator.hasValidCoordinates(createOrderRequest.getDestination())){
            String errorMsg = String.format(Constants.VALIDATION_ERROR_MESSAGE, Constants.INVALID_DESTINATION);
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new ErrorResponse(errorMsg));
        }

        CreateOrderResponse createdOrder = orderService.createNewOrder(createOrderRequest);
        return createdOrder != null ? new Response<>(Constants.HttpStatusCodes.SUCCESS, createdOrder) :
                new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new ErrorResponse(Constants.DISTANCE_UNCALCULABLE));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Take order.")
    public Response<Object> takeOrder(@PathVariable("id") Integer id, @Valid @RequestBody TakeOrderRequest takeOrderRequest, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = String.format(Constants.VALIDATION_ERROR_MESSAGE, Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new ErrorResponse(errorMsg));
        }
        if(!validator.hasValidStatus(takeOrderRequest.getStatus())){
            String errorMsg = String.format(Constants.VALIDATION_ERROR_MESSAGE, takeOrderRequest.getStatus());
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new ErrorResponse(errorMsg));
        }
        if(takeOrderRequest.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_UNASSIGNED)){
            String errorMsg = String.format(Constants.UNMEANINGFUL_ORDER_STATUS, takeOrderRequest.getStatus());
            return new Response<>(Constants.HttpStatusCodes.FORBIDDEN, new ErrorResponse(errorMsg));
        }

        boolean status = orderService.takeOrder(id, takeOrderRequest.getStatus().toUpperCase());
        return status ? new Response<>(Constants.HttpStatusCodes.SUCCESS, new TakeOrderResponse("SUCCESS")) :
                new Response<>(Constants.HttpStatusCodes.CONFLICT, new ErrorResponse(Constants.ORDER_ALREADY_TAKEN));
    }

    @GetMapping()
    public Response<Object> getOrders(@RequestParam(name = "page", defaultValue = "1") String page,
                            @RequestParam(name = "limit", defaultValue = "10") String limit) {
        try {
            int pageNum = Integer.parseInt(page);
            int limitNum = Integer.parseInt(limit);

            List<OrderEntity> orders = orderService.getOrders(pageNum, limitNum);
            return new Response<>(Constants.HttpStatusCodes.SUCCESS, new GetOrderResponse(orders));
        } catch (NumberFormatException e) {
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, new ErrorResponse(Constants.INVALID_PAGE_LIMIT));
        }
    }

}
