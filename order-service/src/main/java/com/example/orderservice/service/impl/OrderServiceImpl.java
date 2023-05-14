package com.example.orderservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.api.exception.NotFoundException;
import com.example.orderdatamodel.entity.enumtype.OrderStatusEnum;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderItemDTO;
import com.example.orderservice.payload.request.OrderItemRequest;
import com.example.orderservice.payload.request.OrderRequest;
import com.example.orderdatamodel.entity.Order;
import com.example.orderdatamodel.entity.OrderItem;
import com.example.orderservice.payload.response.OrderItemResponse;
import com.example.orderservice.payload.response.OrderResponse;
import com.example.orderservice.repository.OrderItemRepository;
import com.example.orderservice.repository.OrderRepository;

import com.example.orderservice.service.OrderService;
import com.example.proxycommon.ebook.payload.response.BookResponse;
import com.example.proxycommon.ebook.proxy.EbookServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final EbookServiceProxy ebookServiceProxy;

    @Override
    public void updateOrderStatus(Integer id) {

        Order order = orderRepo.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("updateOrderStatus error: Not found Order with id: %s", id)
                )
        );

        order.setStatus(OrderStatusEnum.DONE);
        orderRepo.save(order);
    }

    @Override
    public OrderDTO getOrderById(Integer id) {

        return mapToOrderDto(orderRepo.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("getOrderById error: Not found Order with id: %s", id)
                )
        ));
    }

    @Override
    public List<OrderDTO> getOrderByCustomer(Long customerId) {

        return orderRepo.findByUserId(customerId).stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrder() {

        return orderRepo.findAll().stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Integer id) {

        if(orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest, Long userId) {

        Map<Integer, BookResponse> bookMap = new HashMap<>();
        final float[] subTotal = {0f};
        List<OrderItem> items = orderRequest.getOrderItemRequests().stream()
                .map(p -> {
                    BookResponse bookResponse = getBookById(p.getBookId());
                    bookMap.put(p.getBookId(), bookResponse);
                    assert bookResponse != null;
                    subTotal[0] += bookResponse.getPrice() * p.getQuantity();

                    return OrderItem.builder()
                            .bookId(p.getBookId())
                            .price(bookResponse.getPrice())
                            .quantity(p.getQuantity())
                            .build();

                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Order order = orderRepo.save(Order.builder()
                        .note(orderRequest.getNote())
                        .status(OrderStatusEnum.ACTIVE)
                        .subTotal(subTotal[0])
                        .userId(userId)
                .build());
        items.forEach(p -> p.setOrder(order));

        List<OrderItemResponse> itemResponses = orderItemRepo.saveAll(items).stream()
                .map(p -> OrderItemResponse.builder()
                        .id(p.getId())
                        .bookId(p.getBookId())
                        .bookName(bookMap.get(p.getBookId()).getName())
                        .totalPrice(p.getPrice() * p.getQuantity())
                        .quantity(p.getQuantity())
                        .build())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .note(order.getNote())
                .itemResponses(itemResponses)
                .build();
    }

    private OrderDTO mapToOrderDto(Order order) {

        return OrderDTO.builder()
                .id(order.getId())
                .note(order.getNote())
                .userId(order.getUserId())
                .subTotal(order.getSubTotal())
                .status(order.getStatus().name())
                .build();
    }

    private BookResponse getBookById(Integer bookId) {
        BookResponse responses = ebookServiceProxy.getBook(bookId);
        return Objects.isNull(responses) ? null : responses;
    }
} 
