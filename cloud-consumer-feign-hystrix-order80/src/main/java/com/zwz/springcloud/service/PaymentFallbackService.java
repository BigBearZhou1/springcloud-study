package com.zwz.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public String paymentInfo_Ok(Integer id) {
        return "---------------paymentFallbackService:paymentInfo_Ok----------------";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "---------------paymentFallbackService:paymentInfo_TimeOut----------------";
    }

    @Override
    public String paymentCircuitBreaker(Integer id) {
        return "---------------paymentFallbackService:paymentInfo_TimeOut----------------";
    }
}
