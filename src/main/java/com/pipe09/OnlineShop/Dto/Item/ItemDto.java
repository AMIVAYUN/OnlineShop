package com.pipe09.OnlineShop.Dto.Item;

import lombok.Data;

@Data

public class ItemDto {
    private Long Item_ID;
    private String Name;
    private int Price;
    private int StockQuantity;
    private String Description;
    ////
    private int Weight;
    private String MadeIn;
    private String ManufacturedCompany;
    public String imgSrc;
    private String DTYPE;

    /*
    public ItemDto(Item item){
        this.item_id=item.getItem_ID();
        this.Name=item.getName();
        this.Price=item.getPrice();
        this.StockQuantity=item.getStockQuantity();
        this.Description=item.getDescription();
        this.Weight=item.getWeight();
        this.MadeIn=item.getMadeIn();
        this.ManufacturedCompany=item.getManufacturedCompany();
        this.imgSrc=item.getImgSrc();
        this.DTYPE=item.getDTYPE();
    }

     */
}
