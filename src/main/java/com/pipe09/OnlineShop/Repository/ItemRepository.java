package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public List<Item> findAll(){
        String jpql="select i from Item i";
        return em.createQuery(jpql, Item.class).getResultList();
    }

    public List<Item> findAllbyType(String type){

        return em.createQuery("select i from Item i where i.DTYPE=:type").setParameter("type",type).getResultList();

    }


    public List<Item> findAllaboutTools() {
        return em.createQuery("select i from Item i where i.DTYPE=Cutter or i.DTYPE=Drill or i.DTYPE=Grindstone or i.DTYPE=Nipper or i.DTYPE=Wrench ").getResultList();
    }
}
