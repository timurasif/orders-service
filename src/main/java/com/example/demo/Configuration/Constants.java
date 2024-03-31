package com.example.demo.Configuration;

public class Constants {
    public static class HttpStatusCodes {
        public static final int SUCCESS = 200;
        public static final int BAD_REQUEST = 400;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int CONFLICT = 409;

    }

    public static final String[] VALID_ORDER_STATUSES = {"UNASSIGNED", "TAKEN"};
    public static final String ORDER_STATUS_UNASSIGNED = "UNASSIGNED";
    public static final String ORDER_STATUS_TAKEN = "TAKEN";
}
