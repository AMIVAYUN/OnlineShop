package com.pipe09.OnlineShop.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pipe09.OnlineShop.Exception.Class.TossPayError;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.OrderService;
import com.pipe09.OnlineShop.Utils.BASE64Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

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


    @GetMapping("/payments/purchase/success")
    public String paymentSuccess( @RequestParam String orderId, @RequestParam String paymentKey,  @RequestParam int amount,Model model ) throws JsonProcessingException {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        BASE64Utils base64=new BASE64Utils(Base64.getEncoder(),Base64.getDecoder());
        Long order_ID=Long.valueOf(base64.decode(orderId));
        log.info(username+"님 결제 성공 및 리다이렉트"+"주문 번호: "+order_ID.toString());

        if(orderService.SuccessHandle(order_ID,paymentKey,amount)){
            try{
                orderService.getApprovalofPayment(paymentKey,orderId, amount);
                model.addAttribute("message","결제에 성공하셨습니다.");
                return "fragments/private/PayEnd";
            }catch(HttpStatusCodeException e){
                ResponseEntity entity=ResponseEntity.status(e.getRawStatusCode()).header(e.getResponseHeaders().toString()).body(e.getResponseBodyAsString());
                log.info(entity.getBody().toString());
                ObjectMapper mapper = new ObjectMapper();
                TossPayError error = mapper.readValue(entity.getBody().toString(),TossPayError.class);
                model.addAttribute("message",error.getMessage());
                return "fragments/private/PayEnd";
            }


        }



        model.addAttribute("내부 서버에 에러가 있습니다. 잠시 후 다시 시도해주세요.");
        return "fragments/private/PayEnd";
    }

    @GetMapping("/payments/purchase/fail")
    public String paymentFail(@RequestParam String code, @RequestParam String message, @RequestParam String orderId, Model model){
        orderService.getFail(code, message, orderId);
        model.addAttribute("message",message);
        model.addAttribute("orderId",orderId);

        return "fragments/private/PayError";
    }





}
