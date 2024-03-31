package com.example.demo.Exceptions;

import com.example.demo.Configuration.Constants;
import com.example.demo.Models.dto.Response;
import com.example.demo.Models.dto.TakeOrder.TakeOrderResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(OrderNotFoundException.class)
    public Response handleOrderNotFoundException(OrderNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new Response(Constants.HttpStatusCodes.NOT_FOUND, new TakeOrderResponse(null, errorMessage));
    }

    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }
}
