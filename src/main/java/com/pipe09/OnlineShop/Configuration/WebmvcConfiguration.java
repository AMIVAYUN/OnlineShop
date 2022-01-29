package com.pipe09.OnlineShop.Configuration;

import com.pipe09.OnlineShop.Utils.Utils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebmvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("img/**").addResourceLocations("file:////"+Utils.getImgPATHwithOS());
    }
}
