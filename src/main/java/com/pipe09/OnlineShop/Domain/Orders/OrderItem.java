package com.pipe09.OnlineShop.Domain.Orders;


import com.pipe09.OnlineShop.Domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ORDER_ITEM")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int Price;
    private Long OrderItem_ID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Item_ID")
    private Item item;
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
