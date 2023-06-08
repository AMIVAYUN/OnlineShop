package com.pipe09.OnlineShop.Domain.Orders.v2;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItemv2 {
    @Id
    @GeneratedValue
    private Long OrderItem_ID;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Item_ID")
    private Item item;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Order_ID")
    private Orders orders;

    private Integer count;

    public int getTotalPrice(){
        return this.getPrice()*this.getCount();
    }

    public static OrderItem createOrderItem(Item item, int price, int count) throws Exception {
        OrderItem newOrderItem=new OrderItem();
        newOrderItem.setItem(item);
        newOrderItem.setPrice(price);
        newOrderItem.setCount(count);
        item.removeStockQuantity(count);
        return newOrderItem;
    }
    public void cancel(){
        getItem().addStockQuantity(this.count);
    }
}
