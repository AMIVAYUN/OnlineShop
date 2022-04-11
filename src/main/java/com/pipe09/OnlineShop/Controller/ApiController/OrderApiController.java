package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Domain.Payment.payment;
import com.pipe09.OnlineShop.Dto.Order.CreateOrderDto;
import com.pipe09.OnlineShop.Dto.Order.DeliverySetDto;
import com.pipe09.OnlineShop.Dto.Order.OrderDto;
import com.pipe09.OnlineShop.Dto.OrderItem.OrderItemDto;
import com.pipe09.OnlineShop.Dto.Payment.Us_paymentDto;
import com.pipe09.OnlineShop.Dto.Payment.doCancelDto;
import com.pipe09.OnlineShop.Exception.StockLackException;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.Oauth2Service;
import com.pipe09.OnlineShop.Service.OrderService;

import com.pipe09.OnlineShop.Utils.BASE64Utils;
import io.swagger.models.Response;
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

import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<String> createOrders(@Valid @RequestBody CreateOrderDto dto){

        try{
            Long result=orderService.createOrder(dto);
            if(result==-1){
                log.info(dto.getName()+"회원 엔티티 조회 실패");
                return new ResponseEntity<>("회원을 찾을 수 없습니다",HttpStatus.NOT_FOUND);
            }else{
                try{
                    String encoded=orderService.getEncodeOrderId(result);
                    log.info(encoded+ "주문 내역 생성");
                    return new ResponseEntity(encoded,HttpStatus.OK);
                }catch (Exception e){
                    log.info(e.toString());
                }

            }

        }catch(StockLackException e){
            log.info(dto.getItem().toString()+"재고 부족으로 인한 주문 생성 실패");
            return new ResponseEntity("재고 부족",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("서버 에러",HttpStatus.INTERNAL_SERVER_ERROR);




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

    @PostMapping("/payments/doCancel/{paymentKey}")
    public void paymentCancler(@PathVariable String paymentKey,@RequestBody doCancelDto dto){
        orderService.doCancel(paymentKey,dto);

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
