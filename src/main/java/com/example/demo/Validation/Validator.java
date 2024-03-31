package com.example.demo.Validation;

import com.example.demo.Configuration.Constants;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class Validator {

    public boolean hasValidCoordinates(String[] coordinatesArray) {
        if (coordinatesArray == null || coordinatesArray.length != 2) {
            return false;
        }
        for (String coordinate : coordinatesArray) {
            if (coordinate == null || coordinate.isEmpty()) {
                return false;
            }
        }
        if (Double.parseDouble(coordinatesArray[0]) < -90 || Double.parseDouble(coordinatesArray[0]) > 90 ||
                Double.parseDouble(coordinatesArray[1]) < -180 || Double.parseDouble(coordinatesArray[1]) > 180) {
            return false;
        }

        return true;
    }

    public boolean hasValidStatus(String status) {
        return Arrays.stream(Constants.VALID_ORDER_STATUSES).anyMatch(validStatus -> validStatus.equalsIgnoreCase(status));
    }
}