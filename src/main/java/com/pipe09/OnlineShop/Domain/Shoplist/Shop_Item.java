package com.pipe09.OnlineShop.Domain.Shoplist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipe09.OnlineShop.Domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Shop_Item {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Item_ID")
    private Item item;

    private int count;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shoplist_ID")
    private ShopCart shoplist;
    public static Shop_Item makenewShop_Item(Item item,int count,ShopCart cart){
        Shop_Item newitem=new Shop_Item();
        newitem.setShoplist(cart);
        newitem.setItem(item);
        newitem.setCount(count);
        return newitem;
    }
}
