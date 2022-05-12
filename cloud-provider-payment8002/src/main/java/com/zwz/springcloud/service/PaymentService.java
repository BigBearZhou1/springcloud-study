package com.zwz.springcloud.service;


import com.zwz.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(Long id);
}
