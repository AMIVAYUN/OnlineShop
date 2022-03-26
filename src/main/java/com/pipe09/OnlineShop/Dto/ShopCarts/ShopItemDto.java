package com.pipe09.OnlineShop.Dto.ShopCarts;

import com.pipe09.OnlineShop.Domain.Shoplist.Shop_Item;
import lombok.Data;

@Data
public class ShopItemDto {
    private Long item_id;
    private String item_name;
    private int count;
    private int price;
    private String imgSrc;
    public ShopItemDto(Shop_Item item){
        this.item_id=item.getId();
        this.item_name=item.getItem().getName();
        this.count=item.getCount();
        this.price=item.getItem().getPrice();
        this.imgSrc=item.getItem().getImgSrc();
    }
}
