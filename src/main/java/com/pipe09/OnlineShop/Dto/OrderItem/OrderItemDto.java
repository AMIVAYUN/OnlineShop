package com.pipe09.OnlineShop.Dto.OrderItem;

import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private String itemname;
    private int count;
    private int totalprice;

    public OrderItemDto(OrderItem item){
        this.itemname=item.getItem().getName();
        this.count=item.getCount();
        this.totalprice=item.getTotalPrice();
    }



}
