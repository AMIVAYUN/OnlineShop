package com.pipe09.OnlineShop.Configuration;

import com.pipe09.OnlineShop.Utils.Utils;
import org.hibernate.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.util.concurrent.TimeUnit;

//이미지 저장 위치 설정
@Configuration
public class WebmvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        CacheControl staticCacheControl = CacheControl.maxAge(5, TimeUnit.SECONDS);
        CacheControl imgCacheControl = CacheControl.maxAge(20, TimeUnit.MINUTES);

        registry.addResourceHandler("**/*.*").addResourceLocations("classpath:/static/").setCacheControl(staticCacheControl);
        //registry.addResourceHandler("img/upload/**").addResourceLocations("file:////"+Utils.getImgPATHwithOS()+"upload"+ File.separator).setCacheControl(imgCacheControl);
        //registry.addResourceHandler("img/static/**").addResourceLocations("file:////"+Utils.getImgPATHwithOS()+"static"+File.separator).setCacheControl(imgCacheControl);
        registry.addResourceHandler( "img/**" ).addResourceLocations("file:////" + Utils.getImgPATHwithOS() ).setCacheControl(imgCacheControl );

        //swagger
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
    }




}
