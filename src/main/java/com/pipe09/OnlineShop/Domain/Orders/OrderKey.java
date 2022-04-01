package com.pipe09.OnlineShop.Domain.Orders;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "order_key_map")
public class OrderKey {
    @Id
    private String orderKey;

    @Column(name = "order_id",nullable = false)
    private Long order_ID;
}
