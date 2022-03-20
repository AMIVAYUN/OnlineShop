package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Shoplist.ShopCart;
import com.pipe09.OnlineShop.Domain.Shoplist.Shop_Item;
import com.pipe09.OnlineShop.Repository.ShopCartRepository;
import com.pipe09.OnlineShop.Repository.ShopItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ShopCartService {
    private final ShopCartRepository shopCartRepository;
    private final ShopItemRepository shopItemRepository;
    public boolean AppendNewItem(Long Cart_id,Item item,int count){
        ShopCart userCart=shopCartRepository.findById(Cart_id).get();
        Shop_Item newItem=Shop_Item.makenewShop_Item(item,count,userCart);
        if(!findDuplicatedItem(item.getItem_ID(),userCart.getShoplist_ID())){
            userCart.addItem(newItem);
            shopCartRepository.save(userCart);
            return true;
        }else{
            return false;
        }


    }
    public List<Shop_Item> getShopItems(Long Cart_id) {
        return shopItemRepository.findAllbyCartID(Cart_id);
    }
    public boolean findDuplicatedItem(Long item_id,Long Cart_id){
        if(shopItemRepository.findShopItem(item_id,Cart_id).size()!=0){
            return true;
        }else{
            return false;
        }
    }

}
