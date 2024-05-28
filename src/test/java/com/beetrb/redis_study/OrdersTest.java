package com.beetrb.redis_study;

import com.beetrb.redis_study.order.dto.OrderCreateReqDTO;
import com.beetrb.redis_study.order.dto.OrderProduct;
import com.beetrb.redis_study.order.service.OrderService;
import com.beetrb.redis_study.product.domain.ProductRepository;
import com.beetrb.redis_study.product.domain.Products;
import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;
import com.beetrb.redis_study.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class OrdersTest {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductRepository productRepository;
    @Test
    public void createOrders() {

        String[] userIdList = {
            "0FZH1ZKP1R6NA",
            "0G03315ZW6AJT",
            "0G033160G6AGV",
            "0G03316106A9D",
            "0G033161G6AT6",
            "0G03316206A6Q",
            "0G033162G6AS9",
            "0G03316306ARQ",
            "0G033163G6A04",
            "0G03316406A10",
            "0G033164C6ABY",
            "0G033164W6AMQ",
            "0G033165C6A9R",
            "0G03316606A7V",
            "0G033166G6AC3",
            "0G033166W6A5Q",
            "0G03316786AEF",
            "0G033167R6AY5",
            "0G033168C6ANZ",
            "0G03316906A5A"};
        for (int i = 0; i < 10000; i++) {
            User user = User.builder()
                .id(userIdList[i%20])
                .build();
            int amount = (int)(Math.random() * 30) + 1;
            Long productId = Long.valueOf(4182 + (i % 40));
            Products products = productRepository.findById(productId).orElse(null);

            OrderProduct orderProduct = new OrderProduct(amount, products);
            OrderCreateReqDTO orderCreateReqDTO = new OrderCreateReqDTO("테스트주소" + (i % 20), orderProduct);

            orderService.createOrder(orderCreateReqDTO, user);
        }
    }
}
