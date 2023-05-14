package com.example.cartservice.payload.response;

import com.example.proxycommon.ebook.payload.response.BookResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CartItemResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("book")
    private BookResponse book;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("quantity")
    private Integer quantity;
}
