package com.beetrb.redis_study.order.service;

import com.beetrb.redis_study.order.domain.OrderRepository;
import com.beetrb.redis_study.order.domain.Orders;
import com.beetrb.redis_study.order.dto.OrderCreateReqDTO;
import com.beetrb.redis_study.product.domain.ProductRepository;
import com.beetrb.redis_study.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Override
    public void createOrder(OrderCreateReqDTO createReqDTO, User user) {
        Orders orders = Orders.create(createReqDTO, user);
        orderRepository.save(orders);
    }
}
