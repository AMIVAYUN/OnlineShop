package com.pipe09.OnlineShop.Dto;

import com.pipe09.OnlineShop.Domain.Item.Item;
import lombok.Data;


@Data
public class M_ItemDto {
    private Long Item_ID;
    private String Name;
    private int Price;
    public String imgSrc;
    private String dtype;
    private String company;
    private String DTYPE;
    public M_ItemDto(Item item) {
        Item_ID=item.getItem_ID();
        Name=item.getName();
        Price=item.getPrice();
        imgSrc=item.getImgSrc();
        dtype= item.getClass().getName();
        company=item.getManufacturedCompany();
    }
    public void ManuFacDtype(){
        this.setDtype((new StringBuffer(this.DTYPE).replace(0,this.DTYPE.indexOf("Typed")+6,"")).toString());
    }
}
