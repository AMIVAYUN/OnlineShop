package com.pipe09.OnlineShop.Domain.Shoplist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipe09.OnlineShop.Domain.Member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@Entity
public class ShopCart {
    @Id
    @GeneratedValue
    private Long shoplist_ID;
    @OneToMany(mappedBy = "shoplist",cascade = CascadeType.ALL)
    @Column(name = "shop_items")
    private List<Shop_Item> shopItems=new ArrayList<>();

    @OneToOne(mappedBy = "shopCart")
    @JsonIgnore
    private Member member;

    @Transactional
    public void addItem(Shop_Item... items){
        Arrays.stream(items).forEach( shop_item -> this.shopItems.add(shop_item));

    }

}
