package com.zwz.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_Ok(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK, id : " + id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="30000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_Timeout,id: " + id;
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOutHandler,id: " + id;
    }


    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled" ,value = "true"),/*是否开启断路器*/
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold" ,value = "10"),/*请求次数*/
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds" ,value = "10000"),/*时间窗口*/
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage" ,value = "60")/*失败率达到多少后跳闸*/
    })
    public String paymentCircuitBreaker(Integer id){
        if(id<0){
            throw new RuntimeException("***** id 不能为负数");
        }
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+ UUID.randomUUID().toString();
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后再试。id = "+id;
    }

}
