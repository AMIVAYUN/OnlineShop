package com.pipe09.OnlineShop.Controller;


import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.OrderService;
import com.pipe09.OnlineShop.Utils.BASE64Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ItemService itemService;
    @GetMapping("/payments/purchase/single/{itemid}")
    public String getPaymentsSinglePage(){ return "fragments/public/Payment"; }

    @GetMapping("/payments/purchase/bycart")
    public String getPaymentsCartPage(){ return "fragments/public/Payment"; }

    @GetMapping("/payments/credit/bytoss")
    public String getTossPage(){return "fragments/private/TossPay"; }

    @GetMapping("/payments/purchase/endpoint")
    public String getCompletePage() { return "fragments/private/PayEnd";}

    
    @GetMapping("/payments/purchase/fail")
    public String paymentFail(@RequestParam String code, @RequestParam String message, @RequestParam String orderId, Model model){
        orderService.getFail(code, message, orderId);
        model.addAttribute("message",message);
        model.addAttribute("orderId",orderId);

        return "fragments/private/PayError";
    }





}
