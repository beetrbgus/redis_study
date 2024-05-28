package com.beetrb.redis_study.order.controller;

import com.beetrb.redis_study.order.dto.OrderCreateReqDTO;
import com.beetrb.redis_study.order.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public void customerOrder(OrderCreateReqDTO createReqDTO) {
    }
}
