package com.pipe09.OnlineShop.Domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    private Long delivery_ID;
    private Deliverystatus status;
    private String Delivery_Address;

}
