package com.example.demo.Configuration;

public class Constants {
    public static class HttpStatusCodes {
        public static final int SUCCESS = 200;
        public static final int BAD_REQUEST = 400;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int CONFLICT = 409;

    }

    public static final String VALIDATION_ERROR_MESSAGE = "Validation error: %s";
    public static final String INVALID_ORIGIN = "Invalid Origin";
    public static final String INVALID_DESTINATION = "Invalid Destination";
    public static final String DISTANCE_UNCALCULABLE = "Unable to calculate distance between this set of origin and destination.";
    public static final String UNMEANINGFUL_ORDER_STATUS = "Order status not meaningful: %s";
    public static final String ORDER_ALREADY_TAKEN = "Order already taken.";
    public static final String INVALID_PAGE_LIMIT = "Page and Limit must be integers.";


    public static final String[] VALID_ORDER_STATUSES = {"UNASSIGNED", "TAKEN"};
    public static final String ORDER_STATUS_UNASSIGNED = "UNASSIGNED";
    public static final String ORDER_STATUS_TAKEN = "TAKEN";
}
