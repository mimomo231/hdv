package com.example.cartservice.service.impl;

import com.example.api.exception.NotFoundException;
import com.example.cartdatamodel.entity.Cart;
import com.example.cartdatamodel.entity.CartItem;
import com.example.cartdatamodel.entity.enumtype.StatusEnum;
import com.example.cartservice.payload.request.CartItemRequest;
import com.example.cartservice.payload.response.CartItemResponse;
import com.example.cartservice.payload.response.CartResponse;
import com.example.cartservice.repository.CartItemRepository;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.service.CartItemService;
import com.example.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final CartItemService cartItemService;

    @Override
    public List<CartResponse> getAllCarts() {

        return cartRepo.findAll().stream()
                .map(this::mapToCartResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse getActiveCart(Long userId) {

        Cart cart = cartRepo.findByStatusAndUserId(StatusEnum.ACTIVE.name(), userId);
        if(Objects.nonNull(cart)) {
            List<CartItemResponse> cartItemResponses = cart.getItems().stream()
                    .map(cartItemService::mapToCartItemResponse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return CartResponse.builder()
                    .id(cart.getId())
                    .userId(userId)
                    .status(cart.getStatus().name())
                    .items(cartItemResponses)
                    .build();
        }

        return new CartResponse();
    }

    @Override
    public void updateCart(CartItemRequest request, Long userId) {

        Cart cart = cartRepo.findByStatusAndUserId(StatusEnum.ACTIVE.name(), userId);
        if(Objects.nonNull(cart)) {
            //update cart

            Optional<CartItem> cartItem = cart.getItems().stream()
                    .filter(p -> p.getBookId().equals(request.getBookId()))
                    .findFirst();
            if(cartItem.isPresent()) {
                //update cart item - cart item exist

                Integer currQuantity = cartItem.get().getQuantity();
                cartItem.get().setQuantity(currQuantity+1);

                cartItemRepo.save(cartItem.get());
            } else {
                //add cart item - cart item isn't exist

                cartItemRepo.save(CartItem.builder()
                        .cart(cart)
                        .bookId(request.getBookId())
                        .quantity(1)
                        .price(request.getPrice())
                        .build());
            }

        } else {
            //add cart

            Cart createCart = cartRepo.save(Cart.builder()
                    .userId(userId)
                    .status(StatusEnum.ACTIVE)
                    .build());

            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(CartItem.builder()
                    .bookId(request.getBookId())
                    .quantity(1)
                    .price(request.getPrice())
                    .cart(createCart)
                    .build());

            cartItemRepo.saveAll(cartItems);
        }
    }

    @Override
    public void updateStatusCart(Integer cartId) {

        Cart cart = cartRepo.findById(cartId).orElseThrow(
                () -> new NotFoundException(
                        String.format("updateStatusCart error: Not found Cart with id: %s", cartId)
                )
        );
        cart.setStatus(StatusEnum.CHECKOUT);
        cartRepo.save(cart);
    }

    @Override
    public void deleteCart(Integer cartId) {

        Cart cart = cartRepo.findById(cartId).orElseThrow(
                () -> new NotFoundException(
                        String.format("deleteCart error: Not found Cart with id: %s", cartId)
                )
        );
        cartRepo.delete(cart);
    }

    private CartResponse mapToCartResponse(Cart cart) {

        return CartResponse.builder()
                .id(cart.getId())
                .items(cart.getItems().stream()
                        .map(cartItemService::mapToCartItemResponse)
                        .collect(Collectors.toList()))
                .status(cart.getStatus().name())
                .userId(cart.getUserId())
                .build();
    }
}
