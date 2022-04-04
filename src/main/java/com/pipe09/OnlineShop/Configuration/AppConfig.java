package com.pipe09.OnlineShop.Configuration;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
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

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        PoolingHttpClientConnectionManager connectionManager=new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(6);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClients.custom().setConnectionManager(connectionManager).build()));
        return restTemplate;
    }


}