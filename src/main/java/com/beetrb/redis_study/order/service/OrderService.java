package com.beetrb.redis_study.order.service;

import com.beetrb.redis_study.order.dto.OrderCreateReqDTO;
import com.beetrb.redis_study.user.domain.User;

public interface OrderService {
    void createOrder(OrderCreateReqDTO createReqDTO, User user);
}
