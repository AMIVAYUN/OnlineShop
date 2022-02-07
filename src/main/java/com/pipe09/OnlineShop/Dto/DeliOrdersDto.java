package com.pipe09.OnlineShop.Dto;

import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Data;

import java.util.Date;

@Data
public class DeliOrdersDto {
    Long order_ID;
    String membername;
    private Date orderdate;
    public DeliOrdersDto(Orders o){
        this.order_ID=o.getOrder_ID();
        this.membername=o.getMember().getName();
        this.orderdate=o.getOrderdate();
    }
}
