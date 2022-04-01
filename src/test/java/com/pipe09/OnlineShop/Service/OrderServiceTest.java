package com.pipe09.OnlineShop.Service;

//import com.pipe09.OnlineShop.Utils.AES;
import groovy.util.logging.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
@Slf4j
@SpringBootTest
public class OrderServiceTest {
    @Autowired OrderService orderService;
    //@Autowired AES aes;
    /*
    @Test
    public void 암호화테스트() throws Exception {
        AES aes=new AES();
        String text="1";
        System.out.println(aes.encrypt(text));
    }

     */
}