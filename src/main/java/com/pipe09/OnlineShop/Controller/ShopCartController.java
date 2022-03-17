package com.pipe09.OnlineShop.Controller;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ShopCartController {
    @GetMapping("/shopping-list")
    public String getCart(){
        return "fragments/public/ShopCart";
    }
}
