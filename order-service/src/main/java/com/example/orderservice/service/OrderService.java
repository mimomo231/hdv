package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.payload.request.OrderRequest;
import com.example.orderservice.payload.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest, Long userId);

    List<OrderDTO> getAllOrder();

    OrderDTO getOrderById(Integer id);

    List<OrderDTO> getOrderByCustomer(Long userId);

    void deleteOrder(Integer id);

    void updateOrderStatus(Integer id);
}
