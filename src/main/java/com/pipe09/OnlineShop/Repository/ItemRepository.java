package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Item.Item_status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ItemRepository {
    private final EntityManager em;
    public Long save(Item item){
        if(item.getItem_ID()==null){
            em.persist(item);
        }
        else{
            em.merge(item);
        }
        return item.getItem_ID();
    }
    public Item findItem(Long id){
        return em.find(Item.class,id);
    }
    public List<Item> findAll(int offset,int limit){
        String jpql="select i from Item i where i.status=:status";
        return em.createQuery(jpql, Item.class).setParameter("status", Item_status.SALE).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Item> findAllbyType(String type,int offset,int limit){

        return em.createQuery("select i from Item i where i.DTYPE=:type and i.status=:status").setParameter("type",type).setParameter("status",Item_status.SALE).setFirstResult(offset).setMaxResults(limit).getResultList();

    }

    public boolean removeById(Long id){
        Item item=em.find(Item.class,id);
        if(item==null){
            return false;
        }
        item.setStatus(Item_status.DELETED);
        em.flush();
        return true;
    }

    public List<Item> findAllaboutTools() {
        return em.createQuery("select i from Item i where i.status=:status and (i.DTYPE=Cutter or i.DTYPE=Drill or i.DTYPE=Grindstone or i.DTYPE=Nipper or i.DTYPE=Wrench) ").setParameter("status",Item_status.SALE).getResultList();
    }

    public List<Item> findBytitleKeyword(String keyword,int offset,int limit){
        return em.createQuery("select i from Item i where i.status=:status and (i.Name like concat('%',:keyword,'%'))").setParameter("status",Item_status.SALE).setParameter("keyword",keyword).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
