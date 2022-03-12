package com.pipe09.OnlineShop;

import com.pipe09.OnlineShop.Service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;





@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitSerivce initSerivce;
    private final MailService service;

    @PostConstruct
    public void init(){
        initSerivce.dbInit();
        initSerivce.dbInit2();
        service.sendMail();

    }



}

