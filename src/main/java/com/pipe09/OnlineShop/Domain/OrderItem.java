package com.pipe09.OnlineShop.Domain;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderItem {
    @Id
    private Long OrderItem_ID;

    private Long Order_ID;

    private Long TotalPrice;

    private Long count;
}
