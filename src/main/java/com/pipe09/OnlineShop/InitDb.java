package com.pipe09.OnlineShop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;





@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitSerivce initSerivce;

    @PostConstruct
    public void init(){
        initSerivce.dbInit();
        initSerivce.dbInit2();
    }

}

