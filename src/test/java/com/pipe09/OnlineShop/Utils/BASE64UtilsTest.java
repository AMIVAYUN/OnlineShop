package com.pipe09.OnlineShop.Utils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

import static org.junit.Assert.*;


@SpringBootTest
public class BASE64UtilsTest {


    @Test
    public void test(){
        BASE64Utils utils=new BASE64Utils(Base64.getEncoder(),Base64.getDecoder());
        Long test=1L;
        String result=utils.encode(test);
        System.out.println(result);
        System.out.println(utils.decode(result));
        System.out.println(Long.valueOf(utils.decode(result)));

    }

}