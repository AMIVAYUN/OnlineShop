package com.pipe09.OnlineShop.Domain.Orders;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ORDER_ITEM")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    private Long OrderItem_ID;

    private int Price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Item_ID")
    private Item item;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Order_ID")
    private Orders orders;

    private int count;

    public int getTotalPrice(){
        return this.getPrice()*this.getCount();
    }

    public static OrderItem createOrderItem(Item item,int price,int count){
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
