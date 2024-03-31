package com.example.demo.Services;

import com.example.demo.Configuration.Constants;
import com.example.demo.External.Interfaces.MapsService;
import com.example.demo.External.Models.DistanceResponse;
import com.example.demo.Models.Entities.OrderEntity;
import com.example.demo.Models.dto.CreateOrder.CreateOrderRequest;
import com.example.demo.Models.dto.CreateOrder.CreateOrderResponse;
import com.example.demo.Repositories.OrderRepoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class OrderServiceTest {

    final String[] ORIGIN = {"31.482099495239622", "74.2553046355733"};
    final String[] DESTINATION = {"31.452099495239622", "74.2153046355733"};
    final String[] INVALID_ORIGIN = {"33.452099495239622", "75.2153046355733"};
    final String[] INVALID_DESTINATION = {"32.490458998847046", "71.30182196616528"};

    @Mock
    private MapsService googleMapsService;

    @Mock
    private OrderRepoInterface orderRepoInterface;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewOrderSuccess() {
        CreateOrderRequest request = new CreateOrderRequest(ORIGIN, DESTINATION);

        DistanceResponse.Row.Element.Distance distance = new DistanceResponse.Row.Element.Distance("1 km", 1000);
        DistanceResponse.Row.Element distanceElem = new DistanceResponse.Row.Element(distance);
        DistanceResponse.Row row = new DistanceResponse.Row(new DistanceResponse.Row.Element[]{distanceElem});
        DistanceResponse distanceResponse = new DistanceResponse("SUCCESS", new DistanceResponse.Row[]{row});

        when(googleMapsService.getDistance(anyString(), anyString())).thenReturn(distanceResponse);
        OrderEntity savedOrderEntity = new OrderEntity(1, 1000, Constants.ORDER_STATUS_UNASSIGNED);
        when(orderRepoInterface.save(any())).thenReturn(savedOrderEntity);

        CreateOrderResponse response = orderService.createNewOrder(request);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(1000, response.getDistance());
        assertEquals(Constants.ORDER_STATUS_UNASSIGNED, response.getStatus());
    }

    @Test
    void createNewOrderInvalid() {
        CreateOrderRequest request = new CreateOrderRequest(INVALID_ORIGIN, INVALID_DESTINATION);

        DistanceResponse.Row.Element.Distance distance = new DistanceResponse.Row.Element.Distance(null, null);
        DistanceResponse.Row.Element distanceElem = new DistanceResponse.Row.Element(distance);
        DistanceResponse.Row row = new DistanceResponse.Row(new DistanceResponse.Row.Element[]{distanceElem});
        DistanceResponse distanceResponse = new DistanceResponse("FAIL", new DistanceResponse.Row[]{row});

        when(googleMapsService.getDistance(anyString(), anyString())).thenReturn(distanceResponse);

        CreateOrderResponse response = orderService.createNewOrder(request);

        assertNull(response);
    }

}