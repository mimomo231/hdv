package com.example.cartservice.service.impl;

import com.example.api.exception.NotFoundException;
import com.example.cartdatamodel.entity.CartItem;
import com.example.cartservice.payload.response.CartItemResponse;
import com.example.cartservice.repository.CartItemRepository;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.service.CartItemService;
import com.example.proxycommon.ebook.payload.response.BookResponse;
import com.example.proxycommon.ebook.proxy.EbookServiceProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final EbookServiceProxy ebookServiceProxy;

    @Override
    @Transactional
    public void deleteCartItem(Integer cartId) {

        CartItem cartItem = cartItemRepo.findById(cartId).orElseThrow(
                () -> new NotFoundException(
                        String.format("deleteCartItem error: Not found Cart Item with id: %s", cartId)
                )
        );
        cartItemRepo.delete(cartItem);
    }

    private BookResponse getBookById(Integer bookId) {
        BookResponse responses = ebookServiceProxy.getBook(bookId);
        return Objects.isNull(responses) ? null : responses;
    }

    @Override
    public CartItemResponse mapToCartItemResponse(CartItem cartItem) {

        BookResponse bookResponse = getBookById(cartItem.getBookId());
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .book(bookResponse)
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .build();
    }
}
