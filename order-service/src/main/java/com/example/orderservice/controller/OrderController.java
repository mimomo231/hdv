package com.example.orderservice.controller;

import com.example.api.controller.BaseController;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.payload.request.OrderRequest;
import com.example.orderservice.payload.response.OrderResponse;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController extends BaseController {

    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody OrderRequest orderRequest
    ) {

        Long userId = getOriginalId();
        return ResponseEntity.ok(orderService.placeOrder(orderRequest, userId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<OrderDTO>> getAllOrder() {

        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(
            @PathVariable Integer orderId
    ) {

        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/get-order-by-user")
    public ResponseEntity<List<OrderDTO>> getOrderByCustomer() {

        Long userId = getOriginalId();
        return ResponseEntity.ok(orderService.getOrderByCustomer(userId));
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(
            @PathVariable Integer orderId
    ) {

        orderService.deleteOrder(orderId);
        return "Order have been deleted";
    }

    @PostMapping("/update-status/{orderId}")
    public String updateOrderStatus(
            @PathVariable Integer orderId
    ) {

        orderService.updateOrderStatus(orderId);
        return "Order have been updated";
    }
}
