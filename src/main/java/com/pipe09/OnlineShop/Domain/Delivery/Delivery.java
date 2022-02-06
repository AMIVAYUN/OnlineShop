package com.pipe09.OnlineShop.Domain.Delivery;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "DELIVERY")
public class Delivery {
    @Id
    @GeneratedValue
    private Long delivery_ID;
    @JsonIgnore
    @OneToOne(mappedBy = "delivery")
    private Orders order;
    @Enumerated(EnumType.STRING)
    private Deliverystatus status;
    private String Delivery_Address;

}
