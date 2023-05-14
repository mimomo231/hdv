package com.example.orderservice.payload.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String note;

    @JsonProperty("order_items")
    private List<OrderItemRequest> orderItemRequests;
}
