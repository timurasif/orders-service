package com.example.demo.Services;

import com.example.demo.Configuration.Constants;
import com.example.demo.Exceptions.ExceptionHandlers;
import com.example.demo.External.Interfaces.MapsService;
import com.example.demo.External.Models.DistanceResponse;
import com.example.demo.Models.Entities.OrderEntity;
import com.example.demo.Models.dto.CreateOrder.CreateOrderRequest;
import com.example.demo.Models.dto.CreateOrder.CreateOrderResponse;
import com.example.demo.Repositories.OrderRepoInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final MapsService googleMapsService;
    private final OrderRepoInterface orderRepoInterface;
    private final Object lock = new Object();

    public CreateOrderResponse createNewOrder(CreateOrderRequest orderToCreate) {

        String origin = String.join(",", orderToCreate.getOrigin());
        String destination = String.join(",", orderToCreate.getDestination());

        DistanceResponse distanceResponse = googleMapsService.getDistance(origin, destination);
        int distance = distanceResponse.getRows()[0].getElements()[0].getDistance().getValue();

        OrderEntity orderEntity = OrderEntity.builder()
                .distance(distance)
                .status(Constants.ORDER_STATUS_UNASSIGNED)
                .build();

        OrderEntity createdOrder = orderRepoInterface.save(orderEntity);

        return CreateOrderResponse.fromEntity(createdOrder);
    }

    public boolean takeOrder(int id, String status) {
        synchronized (lock){
            OrderEntity orderEntity = orderRepoInterface.findById(id).orElseThrow(() -> new ExceptionHandlers.OrderNotFoundException("Order with ID: " + id + " not found!"));

            if(orderEntity.getStatus().equalsIgnoreCase(Constants.ORDER_STATUS_TAKEN)){
                return false;
            }
            markOrderTaken(orderEntity);
            return true;
        }
    }

    public synchronized void markOrderTaken(OrderEntity orderEntity) {
        orderEntity.setStatus(Constants.ORDER_STATUS_TAKEN);
        orderRepoInterface.saveAndFlush(orderEntity);
    }

    public List<OrderEntity> getOrders(int page, int limit) {
       return null;
    }

}
