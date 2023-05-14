package com.example.orderservice.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("note")
    private String note;

    @JsonProperty("order_items")
    private List<OrderItemResponse> itemResponses;
}
