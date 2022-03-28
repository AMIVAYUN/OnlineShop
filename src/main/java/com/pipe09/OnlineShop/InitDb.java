package com.pipe09.OnlineShop;

import com.pipe09.OnlineShop.Service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitSerivce initSerivce;
    private final MailService service;

    @PostConstruct
    public void init() throws MessagingException, IOException {
        initSerivce.dbInit();
        initSerivce.dbInit2();

    }



}

