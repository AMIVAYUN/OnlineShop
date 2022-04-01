package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Shoplist.Shop_Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShopItemRepository {
    private final EntityManager em;
    //중복체크용
    public List<Shop_Item> findShopItem(Long item_id, Long Cart_id){
        return em.createQuery("select si from Shop_Item si where si.item.Item_ID=:item_id and si.shoplist.shoplist_ID=:Cart_id",Shop_Item.class).setParameter("item_id",item_id).setParameter("Cart_id",Cart_id).getResultList();
    }

    public List<Shop_Item> findAllbyCartID(Long Cart_id){
        return em.createQuery("select si from Shop_Item si where si.shoplist.shoplist_ID=:Cart_id").setParameter("Cart_id",Cart_id).getResultList();
    }
    public boolean deleteIndividual(Long item_id){
        Shop_Item delItem=em.createQuery("select si from Shop_Item  si where si.id=:item_id",Shop_Item.class).setParameter("item_id",item_id).getSingleResult();
        em.remove(delItem);
        return true;
    }
    public Shop_Item findShopItemByShopItemId(Long shopitemId){
        return em.find(Shop_Item.class,shopitemId);
    }

}
