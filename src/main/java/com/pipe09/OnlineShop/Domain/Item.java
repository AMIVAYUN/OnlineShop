package com.pipe09.OnlineShop.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Item {
    @Id
    private Long Item_ID;

    private String Name;
    private Long Price;
    private Long StockQuantity;
    private String Description;
    ////
    private Long Weight;
    private String MadeIn;
    private String ManufacturedCompany;


}
