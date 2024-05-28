package com.beetrb.redis_study.order.domain;

import com.beetrb.redis_study.order.dto.OrderCreateReqDTO;
import com.beetrb.redis_study.product.domain.Products;
import com.beetrb.redis_study.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String address;
    private Integer amount;
    private Integer totalPrice;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private Products products;

    public static Orders create(OrderCreateReqDTO createReqDTO, User user) {
        return Orders.builder()
                    .address(createReqDTO.getAddress())
                    .amount(createReqDTO.getOrderProducts().getAmount())
                    .totalPrice(createReqDTO.getOrderProducts().getProducts().getPrice())
                    .user(user)
                    .products(createReqDTO.getOrderProducts().getProducts())
                    .orderStatus(OrderStatus.DONE)
                    .build();
    }
}