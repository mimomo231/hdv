package com.example.orderservice.dto;

import lombok.Builder;

@Builder
public class OrderItemDTO {

    private Integer id;

    private Float price;

    private Integer quantity;

    private Integer bookId;

    private Integer orderId;
}
