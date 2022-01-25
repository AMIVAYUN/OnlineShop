package com.pipe09.OnlineShop.Dto;

import com.pipe09.OnlineShop.Domain.Item.Item;
import lombok.Data;

@Data
public class M_ItemDto {
    private Long Item_ID;
    private String Name;
    private int Price;
    public String imgSrc;

    public M_ItemDto(Item item) {
        Item_ID=item.getItem_ID();
        Name=item.getName();
        Price=item.getPrice();
        imgSrc=item.getImgSrc();

    }

    ;
}
