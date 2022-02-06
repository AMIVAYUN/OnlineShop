package com.pipe09.OnlineShop.Dto;


import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long OrderId;
    private String user_id;
    private String user_name;
    private List<OrderItemDto> orderItemDto;
    private Delivery delivery;
    private int Totalprice;
    @Temporal(TemporalType.DATE)
    private Date orderdate;
    public OrderDto(Orders order){
        this.OrderId=order.getOrder_ID();
        this.user_id=order.getMember().getUser_ID();
        this.user_name=order.getMember().getName();
        this.orderdate=order.getOrderdate();
        this.delivery=order.getDelivery();
        order.getOrderItems().stream().forEach(item -> item.getItem().getName());
        this.orderItemDto=order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}
