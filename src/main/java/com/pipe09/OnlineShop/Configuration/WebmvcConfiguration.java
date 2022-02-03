package com.pipe09.OnlineShop.Configuration;

import com.pipe09.OnlineShop.Utils.Utils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;


@Configuration
public class WebmvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("img/upload/**").addResourceLocations("file:////"+Utils.getImgPATHwithOS()+"upload"+ File.separator);
        registry.addResourceHandler("img/static/**").addResourceLocations("file:////"+Utils.getImgPATHwithOS()+"static"+File.separator);
    }
}
