package com.pipe09.OnlineShop.Dto.ShopCarts;

import lombok.Data;

@Data
public class ShopCartAppendDto {
    private String username;
    private Long item_id;
    private int count;


}
