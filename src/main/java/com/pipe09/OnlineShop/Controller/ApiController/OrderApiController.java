package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Domain.Payment.payment;
import com.pipe09.OnlineShop.Dto.Order.CreateOrderDto;
import com.pipe09.OnlineShop.Dto.Order.DeliverySetDto;
import com.pipe09.OnlineShop.Dto.Order.OrderDto;
import com.pipe09.OnlineShop.Dto.Order.Us_Orders;
import com.pipe09.OnlineShop.Dto.OrderItem.OrderItemDto;
import com.pipe09.OnlineShop.Dto.Payment.Us_paymentDto;
import com.pipe09.OnlineShop.Dto.Payment.doCancelDto;
import com.pipe09.OnlineShop.Exception.StockLackException;
import com.pipe09.OnlineShop.Service.MemberService;
//import com.pipe09.OnlineShop.Service.Oauth2Service;
import com.pipe09.OnlineShop.Service.OrderService;

import com.pipe09.OnlineShop.Utils.BASE64Utils;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.NoResultException;
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
        log.info(orders.get(0).getOrder_ID().toString());

        List<OrderDto> orderDtos=orders.stream().map(OrderDto::new).collect(Collectors.toList());

        return orderDtos;
    }
    @GetMapping("/api/v1/orders/paid/byuser")
    public ResponseEntity<List<Us_Orders>> getOrdersbyUser( @AuthenticationPrincipal UserDetails details ,@RequestParam int offset, @RequestParam int limit){
        if( details == null ){
            return new ResponseEntity( "권한이 없습니다.",HttpStatus.BAD_REQUEST );
        }
        try{
            List<Orders> orders= orderService.findPaidsByuser( details.getUsername() , offset , limit);
            List<Us_Orders> dtoList=orders.stream().map(Us_Orders::new).collect(Collectors.toList());
            return new ResponseEntity(dtoList,HttpStatus.OK);
        }catch ( Exception e ){
            log.info( e.toString() );
            return new ResponseEntity("내부 서버에 문제가 발생하였습니다.",HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @PostMapping("/api/v1/orders/create")
    public ResponseEntity<String> createOrders(@Valid @RequestBody CreateOrderDto dto ,@AuthenticationPrincipal UserDetails details){

        try{
            Long result=orderService.createOrder(dto,details.getUsername());

            try{
                String encoded=orderService.getEncodeOrderId(result);
                log.info(encoded+ "주문 내역 생성");
                return new ResponseEntity(encoded,HttpStatus.OK);
            }catch (Exception e){
                log.info("주문내역 생성 실패: "+ e.toString());
            }


        }catch(StockLackException e){
            log.info(dto.getItem().toString()+"재고 부족으로 인한 주문 생성 실패");
            return new ResponseEntity("재고 부족가 부족합니다",HttpStatus.BAD_REQUEST);
        }catch(NoResultException e){
            log.info("회원 정보 에러");
            return new ResponseEntity<>("회원 정보 인증간 에러가 발생하였습니다.",HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity<>("내부 서버 에러",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("서버 내부에 문제가 발생하였습니다. 잠시 후 다시 시도해주세요",HttpStatus.INTERNAL_SERVER_ERROR);




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
    /*
    @PutMapping("/admin/manage/orders/upto-delivery")
    public ResponseEntity setupDelivery(@RequestParam(value = "option")int option,@RequestBody DeliverySetDto dto){
        try{
            orderService.ChangeStat(dto.getOrder_id(),option);
        }catch(Exception e){
            return new ResponseEntity(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

     */
    @PutMapping("/admin/manage/orders/changeStat")
    public ResponseEntity setUpDeliveryStat(@RequestParam(value = "option") int option, @RequestBody DeliverySetDto dto){
        try{
            orderService.ChangeStat(dto.getOrder_id(),option);
        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("/admin/manage/orders/registerTransPort")
    public ResponseEntity setTransportDoc(@RequestParam String transPort,@RequestParam Long order_id){
        try{
            orderService.CreateTransPortNum(transPort,order_id);

        }catch(Exception e){
            return new ResponseEntity(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/payments/doCancel/{paymentKey}")
    public ResponseEntity paymentCancler(@PathVariable String paymentKey, @RequestBody doCancelDto dto, @AuthenticationPrincipal UserDetails user){
        try{

            if(user==null){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            orderService.doCancel(paymentKey,dto);
            return new ResponseEntity(HttpStatus.OK);
        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @PostMapping("/payments/doCompleteTrade/{paymentKey}")
    public ResponseEntity paymentCompleter(@PathVariable String paymentKey,@AuthenticationPrincipal UserDetails details){
        try{
            String name=details.getUsername();
            if(name==null){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            orderService.CompleteOrder(paymentKey);
            return new ResponseEntity(HttpStatus.OK);
        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
    @PostMapping("/payments/Canceled")
    public ResponseEntity BeforePayCanceled(@RequestParam String orderId){
        try{
            boolean result = orderService.CancelBeforePay(orderId);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

     */


    



    /*
    @GetMapping("/api/v2/orders/all")
    public List<OrderDto> getOrders(@RequestParam(value = "offset") int offset,@RequestParam int limit){
        List<Orders> orders=orderService.findAllwithToOne(offset,limit);

        List<OrderDto> orderDtos=orders.stream().map(OrderDto::new).collect(Collectors.toList());

        return orderDtos;
    }

     */
}
