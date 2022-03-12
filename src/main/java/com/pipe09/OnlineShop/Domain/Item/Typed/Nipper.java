package com.pipe09.OnlineShop.Domain.Item.Typed;

import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Item.Item_status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("N")
@Getter
@Setter
public class Nipper extends Item {
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
    private Item_status status;
}
