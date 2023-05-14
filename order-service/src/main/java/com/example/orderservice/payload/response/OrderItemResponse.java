package com.example.orderservice.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class OrderItemResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("total_price")
    private Float totalPrice;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("book_name")
    private String bookName;
}
