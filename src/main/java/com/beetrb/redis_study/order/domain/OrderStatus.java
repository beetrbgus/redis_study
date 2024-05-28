package com.beetrb.redis_study.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    READY("결제 준비 중"),
    IN_PROGRESS("결제 진행 중"),
    DONE("결제 승인 완료"),
    CANCELED("결제 취소 됨"),
    ABORTED("결제 승인 실패"),
    ;
    
    private final String desc;
}
