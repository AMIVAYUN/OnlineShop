package com.pipe09.OnlineShop.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class ItemController {
    @GetMapping("/products/{itemid}")
    public String getItemPage(@PathVariable Long itemid){
        return "fragments/public/iteminfo";
    }



}
