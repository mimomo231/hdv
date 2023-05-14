package com.example.cartservice.service;

import com.example.cartdatamodel.entity.CartItem;
import com.example.cartservice.payload.response.CartItemResponse;

public interface CartItemService {

    void deleteCartItem(Integer cartId);

    CartItemResponse mapToCartItemResponse(CartItem cartItem);
}
