package com.pipe09.OnlineShop.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="ITEM")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Item_ID;

    private String Name;
    private Long Price;
    private Long StockQuantity;
    @Lob
    private String Description;
    ////
    private Long Weight;
    private String MadeIn;
    private String ManufacturedCompany;


}
