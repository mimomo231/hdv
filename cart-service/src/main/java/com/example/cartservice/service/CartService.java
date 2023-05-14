package com.example.cartservice.service;

import com.example.cartservice.payload.request.CartItemRequest;
import com.example.cartservice.payload.response.CartResponse;

import java.util.List;

public interface CartService {

    List<CartResponse> getAllCarts();

    CartResponse getActiveCart(Long userId);

    void updateCart(CartItemRequest request, Long userId);

    void updateStatusCart(Integer cartId);

    void deleteCart(Integer cartId);
}
