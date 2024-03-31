package com.example.demo.External.Interfaces;

import com.example.demo.External.Models.DistanceResponse;

public interface MapsService {
    DistanceResponse getDistance(String origin, String destination);
}
