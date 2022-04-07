package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Domain.Payment.payment;
import com.pipe09.OnlineShop.Dto.Order.CreateOrderDto;
import com.pipe09.OnlineShop.Dto.Order.DeliverySetDto;
import com.pipe09.OnlineShop.Dto.Order.OrderDto;
import com.pipe09.OnlineShop.Dto.OrderItem.OrderItemDto;
import com.pipe09.OnlineShop.Dto.Payment.Us_paymentDto;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.Oauth2Service;
import com.pipe09.OnlineShop.Service.OrderService;

import com.pipe09.OnlineShop.Utils.BASE64Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
    @GetMapping("/api/v1/payments/all")
    public ResponseEntity getPaymentByUser(@RequestParam(value = "offset")int offset, @RequestParam(value = "limit")int limit){
        List<payment>payments=orderService.getListByUser(offset, limit);
        if(payments==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            List<Us_paymentDto>dtoList=payments.stream().map(payment -> {
                String orderId=payment.getOrderId();
                List<OrderItem> itemList=orderService.getOrderItemsByEncodedOrderID(orderId);
                return new Us_paymentDto(payment,itemList);
            }).collect(Collectors.toList());
            return new ResponseEntity<List<Us_paymentDto>>(dtoList, HttpStatus.OK);
        }
    }
    @PutMapping("/admin/manage/orders/upto-delivery")
    public ResponseEntity setupDelivery(@RequestBody DeliverySetDto dto){
        try{
            orderService.ChangeStatToDelivery(dto.getOrder_id());
        }catch(Exception e){
            return new ResponseEntity(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
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
