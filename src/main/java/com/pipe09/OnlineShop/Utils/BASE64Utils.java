package com.pipe09.OnlineShop.Utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class BASE64Utils {
    private final Base64.Encoder encoder;
    private final Base64.Decoder decoder;


    public String translateSecKey(String key){
        try{
            byte[] En_key=encoder.encode(key.getBytes());
            return new String(En_key);
        }catch(Exception e){
            log.info(e.toString());
            return null;
        }
    }
    public String encode(Long value){

        try{
            String padding="00";
            String str=value.toString();
            padding+=str;
            byte[] encodedstr=encoder.encode(padding.getBytes());
            return new String(encodedstr);
        }catch (Exception e){
            log.info(e.toString());
            return null;

        }
    }
    public String decode(String value){

        try{
            byte[] decodedstr=decoder.decode(value.getBytes());
            String str= new String(decodedstr);
            log.info(str);
            return new String(decodedstr);
        } catch (Exception e) {
            log.info(e.toString());
            return null;
        }
    }
}
