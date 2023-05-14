package com.example.orderservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("book_id")
    private int bookId;
}
