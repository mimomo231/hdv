package com.example.cartservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CartItemRequest {

    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("price")
    private Float price;
}
