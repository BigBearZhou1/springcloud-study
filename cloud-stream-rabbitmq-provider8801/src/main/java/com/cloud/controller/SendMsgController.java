package com.cloud.controller;

import com.cloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMsgController {
    @Resource
    IMessageProvider messageProvider;

    @GetMapping("/send")
    public String sendMessage(){
        String serial = messageProvider.send();
        return serial;
    }
}
