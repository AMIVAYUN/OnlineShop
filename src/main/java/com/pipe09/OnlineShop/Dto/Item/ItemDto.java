package com.pipe09.OnlineShop.Dto.Item;

import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
    public ItemDto( Itemv2 v2 ){

        this.Item_ID = v2.getItem_ID();
        this.Name = v2.getName();
        this.Price = v2.getPrice();
        this.StockQuantity = v2.getStockQuantity();
        this.Description = v2.getDescription();
        ////
        this.Weight = v2.getWeight();
        this.MadeIn = v2.getMadeIn();
        this.ManufacturedCompany = v2.getManufacturedCompany();
        this.imgSrc = v2.getImgSrc();
        this.DTYPE = v2.getDType().getName();

    }
}
