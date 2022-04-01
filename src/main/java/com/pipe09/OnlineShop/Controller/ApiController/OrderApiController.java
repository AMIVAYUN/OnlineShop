package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Dto.Order.CreateOrderDto;
import com.pipe09.OnlineShop.Dto.Order.OrderDto;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.Oauth2Service;
import com.pipe09.OnlineShop.Service.OrderService;

import com.pipe09.OnlineShop.Utils.BASE64Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    @GetMapping("/api/v1/orders/all")
    public List<OrderDto> getOrders(@RequestParam(value = "offset") int offset,@RequestParam int limit){
        List<Orders> orders=orderService.findAllwithToOne(offset,limit);

        List<OrderDto> orderDtos=orders.stream().map(OrderDto::new).collect(Collectors.toList());

        return orderDtos;
    }

    @PostMapping("/api/v1/orders/create")
    public String createOrders(@Valid @RequestBody CreateOrderDto dto){


        Long result=orderService.createOrder(dto);
        if(result==-1){
            return result.toString();
        }else{
            try{
                String encoded=orderService.getEncodeOrderId(result);
                return encoded;
            }catch (Exception e){
                log.info(e.toString());
            }

        }
        return "-2";


    }

    @GetMapping("/payments/purchase/success")
    public void paymentSuccess(@RequestParam String paymentKey,@RequestParam String orderId,@RequestParam int amount){
        BASE64Utils base64=new BASE64Utils(Base64.getEncoder(),Base64.getDecoder());
        Long order_ID=Long.valueOf(base64.decode(orderId));
        Orders order=orderService.findOne(order_ID);
        if(orderService.SuccessHandle(order,paymentKey,amount)){
            orderService.getApprovalofPayment(paymentKey,orderId,amount);
        }


    }

    @GetMapping("/payments/purchase/fail")
    public void paymentFail(@RequestParam String code,@RequestParam String message,@RequestParam String orderId){

    }

    /*
    @GetMapping("/api/v2/orders/all")
    public List<OrderDto> getOrders(@RequestParam(value = "offset") int offset,@RequestParam int limit){
        List<Orders> orders=orderService.findAllwithToOne(offset,limit);

        List<OrderDto> orderDtos=orders.stream().map(OrderDto::new).collect(Collectors.toList());

        return orderDtos;
    }

     */
}
