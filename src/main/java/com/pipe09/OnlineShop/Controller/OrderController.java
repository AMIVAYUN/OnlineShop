package com.pipe09.OnlineShop.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class OrderController {
    @GetMapping("/payments/purchase/single/{itemid}")
    public String getPaymentsSinglePage(){ return "fragments/public/Payment"; }

    @GetMapping("/payments/purchase/bycart")
    public String getPaymentsCartPage(){ return "fragments/public/Payment"; }


}
