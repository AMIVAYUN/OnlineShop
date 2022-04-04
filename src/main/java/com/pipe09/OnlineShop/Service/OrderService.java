package com.pipe09.OnlineShop.Service;


//import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.UserType;
import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Domain.Payment.Failure;
import com.pipe09.OnlineShop.Domain.Payment.payment;
import com.pipe09.OnlineShop.Domain.SessionUser;
import com.pipe09.OnlineShop.Domain.Shoplist.Shop_Item;
import com.pipe09.OnlineShop.Dto.Order.CreateOrderDto;
import com.pipe09.OnlineShop.Dto.Payment.approvePaymentDto;
import com.pipe09.OnlineShop.Dto.Payment.requestApproveDto;
import com.pipe09.OnlineShop.Repository.*;
//import com.pipe09.OnlineShop.Utils.AES;
import com.pipe09.OnlineShop.Utils.BASE64Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ShopItemRepository shopItemRepository;
    private final Oauth2Service oauth2Service;
    private final ItemRepository itemRepository;
    private final PaymentRepository paymentRepository;
    @Value("${tosspayment.secret}")
    private String SecretKey;
    private final RestTemplate rt;
    /*
    public Long order(String memberId,Long itemId, int count){
        Member member= memberRepository.findByuserId(memberId);
        Item item= itemRepository.findItem(itemId);


        OrderItem orderItem=OrderItem.createOrderItem(item,item.getPrice(),count);
        Orders newOrder=new Orders();
        newOrder.getOrderItems().add(orderItem);
        newOrder.setOrderdate(new Date());
        newOrder.setMember(member);
        newOrder.setDeliverystatus(Deliverystatus.READY);
        orderRepository.save(newOrder);
        return newOrder.getOrder_ID();

    }

     */
    public String getEncodeOrderId(Long value){
        try{
            BASE64Utils utils=new BASE64Utils(Base64.getEncoder(),Base64.getDecoder());
            String encoded=utils.encode(value);
            return encoded;
        }catch (Exception e){
            return e.toString();
        }

    }
    public Orders findOne(Long id){
        return orderRepository.findOne(id);
    }
    @Transactional
    public void cancelOrder(Long orderId){
        Orders order= orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Orders> findAllwithToOne(int offset, int limit) {
        return orderRepository.findAllwithToOne(offset,limit);
    }

    public List<OrderItem> findOrderItems(Long id) {
        return orderRepository.findOrderItems(id);

    }
    @Transactional
    public Long createOrder(CreateOrderDto dto){

        Member member=null;
        SessionUser user=(SessionUser) oauth2Service.getHttpSession().getAttribute("user");
        if(user==null){
           member=memberRepository.findByNameWithOauth(dto.getName(), UserType.LOCAL);
        }else{
            switch(user.getType()){
                case "kakao":
                    member=memberRepository.findByNameWithOauth(dto.getName(),UserType.KAKAO);
                    break;
                case "google":
                    member=memberRepository.findByNameWithOauth(dto.getName(),UserType.GOOGLE);
                    break;

            }
        }

        if(member==null){
            return -1L;
        }
        List<OrderItem> orderItemList=dto.getItem().stream().map(item -> {

            Orders newOrder=new Orders();
            OrderItem orderItem= new OrderItem();
            Item findItem;
            log.info(item.getItem_id().toString());
            if(dto.getType().equals("single")){
                findItem=itemRepository.findItem(item.getItem_id());
            }else{
                findItem=shopItemRepository.findShopItemByShopItemId(item.getItem_id()).getItem();
            }


            orderItem.setItem(findItem);
            orderItem.setCount(item.getCount());
            orderItem.setPrice(findItem.getPrice());

            orderItem.setOrders(newOrder);
            return orderItem;

        }).collect(Collectors.toList());
        Orders order=Orders.createOrder(member,new Date(),orderItemList,dto.getTotalprice());
        orderRepository.save(order);
        return order.getOrder_ID();
    }
    @Transactional
    public boolean SuccessHandle(Orders order,String paymentKey,int amount){
        order.setDeliverystatus(Deliverystatus.READY);
        order.setPaymentKey(paymentKey);
        if(order.getTotalPrice()==amount){
            return true;
        }else{
            return false;

        }

    }
    public HttpHeaders getRequestTossHeaders(){
        HttpHeaders headers=new HttpHeaders();
        BASE64Utils base64 = new BASE64Utils(Base64.getEncoder(),Base64.getDecoder());
        String En_secretkey=base64.translateSecKey(this.SecretKey+":");
        headers.add("Authorization"," Basic "+En_secretkey);
        headers.add("Content-Type","application/json");
        return headers;
    }
    /*
    public MultiValueMap<String,Object> getEntity(String orderId,int amount){
        MultiValueMap<String,Object> params=new LinkedMultiValueMap<>();
        params.add("orderId",orderId);
        params.add("amount",amount);
        return params;
    }

     */
    public String getApprovalofPayment(String paymentKey,String orderId, int amount){
        requestApproveDto entity=new requestApproveDto();
        entity.setAmount(amount);
        entity.setOrderId(orderId);
        HttpHeaders headers=getRequestTossHeaders();
        HttpEntity<requestApproveDto> sendData= new HttpEntity<>(entity,headers);
        /*
        requestApproveDto entity=new requestApproveDto();
        entity.setOrderId(orderId);
        entity.setAmount(amount);

         */
        //보내기

        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        String url="https://api.tosspayments.com/v1/payments/"+paymentKey;
        ResponseEntity<approvePaymentDto> response=rt.exchange(
                url,
                HttpMethod.POST,
                sendData,
                approvePaymentDto.class

        );
        log.info(response.getStatusCode() +": "+ response.getStatusCodeValue());
        if(response.getStatusCodeValue()==200){
            String result=processPaymentApprove(response.getBody());
            log.info(result+" Entity 결제 완료 및 저장 ");
            return String.valueOf(response.getStatusCodeValue());
        }else{
            return response.getBody().getMessage();
        }
    }



    @Transactional
    public String processPaymentApprove(approvePaymentDto dto){
        payment payment=new payment(dto);
        String user_id=getLoginUsername();
        Member member=memberRepository.findByuserId(user_id);
        payment.setMember(member);
        paymentRepository.save(payment);
        return payment.getPaymentKey();
    }

    @Transactional
    public boolean getFail(String code,String message,String orderId){
        payment payment=paymentRepository.findByOrderId(orderId);
        if(payment.isNotfound()){
            return false;
        }else{
            Failure failure=new Failure();
            failure.setCode(code);
            failure.setMessage(message);
            payment.setFailure(failure);
            return true;
        }

    }

    public List<payment> getListByUser(int offset,int limit){
        String user_id=getLoginUsername();
        List<payment> paymentList=paymentRepository.findByUserId(user_id,offset,limit);
        if(paymentList==null){
            return null;
        }else{
            return paymentList;
        }

    }

    public String getLoginUsername(){
        SessionUser user=(SessionUser) oauth2Service.getHttpSession().getAttribute("user");
        if(user==null){
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }else{
            return user.getEmail();
        }
    }
    public List<OrderItem> getOrderItemsByEncodedOrderID(String orderId){
        BASE64Utils utils=new BASE64Utils(Base64.getEncoder(),Base64.getDecoder());
        Long order_id=Long.valueOf(utils.decode(orderId));
        List<OrderItem> itemList=orderRepository.findOrderItems(order_id);
        if(itemList==null){
            return null;
        }
        return orderRepository.findOrderItems(order_id);
    }
}
