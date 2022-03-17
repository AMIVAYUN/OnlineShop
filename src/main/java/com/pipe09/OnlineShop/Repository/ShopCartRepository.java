package com.pipe09.OnlineShop.Repository;


import com.pipe09.OnlineShop.Domain.Shoplist.ShopCart;
import com.pipe09.OnlineShop.Domain.Shoplist.Shop_Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository

public interface ShopCartRepository extends JpaRepository<ShopCart,Long>{



    @Query(value = "select c from ShopCart c where c.member.Member_ID=:member_id")
    public ShopCart findShopCartByMemberId(@Param("member_id") Long member_id);


    @Query(value="select i from Shop_Item i where i.shoplist.shoplist_ID=:shoplist_id and i.id=:shopitem_id")
    public List<Shop_Item> findShopitemByShoplist_ID(@Param("shoplist_id")Long shoplist_id, @Param("shopitem_id")Long shopitem_id);



    @Transactional
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = "delete from Shop_Item i where i.shoplist.shoplist_ID in :shoplist_id")
    public void removeAllofItemAboutShoplist(@Param("shoplist_id") Long id);



}
