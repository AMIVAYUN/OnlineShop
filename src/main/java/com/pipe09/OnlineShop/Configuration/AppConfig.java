package com.pipe09.OnlineShop.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;


@Configuration
public class AppConfig {
    private final int FILE_MAX_UPLOAD_SIZE=10485760;
    @Bean
    public MultipartResolver multipartResolver(){
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(FILE_MAX_UPLOAD_SIZE);
        return multipartResolver;
    }

}
