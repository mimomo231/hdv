package com.example.cartservice.controller;

import com.example.cartservice.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "api/cart-item")
public class CartItemController {

    private final CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable Integer cartItemId
    ) {

        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.ok("Delete Cart Item success!");
    }
}
