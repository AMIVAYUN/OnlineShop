package com.pipe09.OnlineShop.Domain.Delivery;


import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "DELIEVERY")
public class Delivery {
    @Id
    @GeneratedValue
    private Long delivery_ID;
    @OneToOne(mappedBy = "delivery")
    private Orders order;
    @Enumerated(EnumType.STRING)
    private Deliverystatus status;
    private String Delivery_Address;

}
