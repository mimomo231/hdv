package com.example.cartservice.controller;

import com.example.api.controller.BaseController;
import com.example.cartservice.payload.request.CartItemRequest;
import com.example.cartservice.payload.response.CartResponse;
import com.example.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "api/cart")
public class CartController extends BaseController {

    private final CartService cartService;

    @GetMapping("/all-cart")
    public ResponseEntity<List<CartResponse>> getAll() {

        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/active-cart")
    public ResponseEntity<CartResponse> getActiveCart() {

        Long userId = getOriginalId();
        return ResponseEntity.ok(cartService.getActiveCart(userId));
    }

    @PostMapping("/update-cart")
    public ResponseEntity<?> updateCart(
            @RequestBody CartItemRequest request
    ) {

        Long userId = getOriginalId();
        cartService.updateCart(request, userId);
        return ResponseEntity.ok("Update Cart success!");
    }

    @PostMapping("/update-status/{cartId}")
    public ResponseEntity<?> updateStatusCart(
            @PathVariable Integer cartId
    ) {

        cartService.updateStatusCart(cartId);
        return ResponseEntity.ok("Update Status Cart success!");
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(
            @PathVariable Integer cartId
    ) {

        cartService.deleteCart(cartId);
        return ResponseEntity.ok("Delete Cart success!");
    }
}
