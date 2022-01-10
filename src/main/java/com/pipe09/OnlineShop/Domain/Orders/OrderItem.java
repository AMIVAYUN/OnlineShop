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
    private Long OrderItem_ID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Item_ID")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Order_ID")
    private Orders orders;

    private Long Price;

    private Long count;

    public Long getTotalPrice(){
        return this.getPrice()*this.getCount();
    }
}
