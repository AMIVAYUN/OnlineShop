package com.pipe09.OnlineShop.Domain.Item.Typed;


import com.pipe09.OnlineShop.Domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("E")
@Getter
@Setter
public class Endoscope extends Item {
    private Long Item_ID;
    private String Name;
    private int Price;
    private int StockQuantity;
    private String Description;
    private int Weight;
    private String MadeIn;
    private String ManufacturedCompany;
    public String imgSrc;
    private String DTYPE;
}
