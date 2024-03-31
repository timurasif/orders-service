package com.example.demo.External;

import com.example.demo.External.Interfaces.MapsService;
import com.example.demo.External.Models.DistanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsServiceImpl implements MapsService {

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${google.maps.endpoint}")
    private String mapsEndpoint;

    @Value("${google.maps.api-key}")
    private String apiKey;

    public GoogleMapsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public DistanceResponse getDistance(String origin, String destination) {

        String apiUrl = String.format("%s/json?origins=%s&destinations=%s&key=%s", mapsEndpoint, origin, destination, apiKey);
        return restTemplate.getForObject(apiUrl, DistanceResponse.class);
    }

}
