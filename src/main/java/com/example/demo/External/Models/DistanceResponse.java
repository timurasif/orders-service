package com.example.demo.External.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistanceResponse {
    private String status;
    private Row[] rows;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Row {
        private Element[] elements;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Element {
            private Distance distance;

            @Getter
            @Setter
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Distance {
                @JsonProperty("text")
                private String text;
                @JsonProperty("value")
                private Integer value;

            }
        }
    }
}
