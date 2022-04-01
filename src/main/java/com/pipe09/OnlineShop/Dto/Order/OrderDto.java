package com.pipe09.OnlineShop.Dto.Order;


//import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Dto.OrderItem.OrderItemDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Utils.Utils;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long OrderId;
    private String user_id;
    private String user_name;
    private String address;
    private List<OrderItemDto> orderItemDto;
    private String deliverystatus;
    private int Totalprice=0;
    @Temporal(TemporalType.DATE)
    private Date orderdate;
    /*
    public void getItemName(Orders order){
        order.getOrderItems().stream().forEach(item -> item.getItem().getName());
    }
    public void getOrderItemDto(Orders order){
        DefaultMapper<OrderItemDto> mapper=new DefaultMapper<>(new OrderItemDto());
        this.orderItemDto=order.getOrderItems().stream().map(orderitem -> mapper.Translate(orderitem)).collect(Collectors.toList());
        orderItemDto.stream().forEach(orderItemDto1 -> Totalprice);
    }

     */

    public OrderDto(Orders order){
        this.OrderId=order.getOrder_ID();
        this.user_id=order.getMember().getUser_ID();
        this.user_name=order.getMember().getName();
        this.orderdate=order.getOrderdate();
        this.deliverystatus= Utils.getAnnouncedDeliverystatus(order.getDeliverystatus());
        this.address=order.getMember().getAddress().getPostcode()+" "+order.getMember().getAddress().getAddress()+" "+order.getMember().getAddress().getDetailAddress()+"("+order.getMember().getAddress().getRef_address()+")";
        order.getOrderItems().stream().forEach(item -> item.getItem().getName());
        this.orderItemDto=order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        orderItemDto.stream().forEach(orderItemDto1 -> {
            Totalprice+=orderItemDto1.getTotalprice();
        });
    }


}
