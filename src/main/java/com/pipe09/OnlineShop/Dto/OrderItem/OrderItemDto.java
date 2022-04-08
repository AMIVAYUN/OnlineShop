package com.pipe09.OnlineShop.Dto.OrderItem;

import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long orderitem_id;
    private Long item_id;
    private String imgSrc;
    private String itemname;
    private int count;
    private int price;

    public OrderItemDto(OrderItem item){
        this.imgSrc=item.getItem().getImgSrc();
        this.orderitem_id=item.getOrderItem_ID();
        this.item_id=item.getItem().getItem_ID();
        this.itemname=item.getItem().getName();
        this.count=item.getCount();
        this.price=item.getPrice();
        this.imgSrc=item.getItem().getImgSrc();
    }



}
