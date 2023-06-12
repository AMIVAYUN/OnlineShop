package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Item.V1.Item_status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Item findItemwithLock(Long id){
        Map<String,Object> properties = new HashMap<>();
        properties.put( "javax.persistence.lock.timeout", 10000 );

        return em.find(Item.class,id, LockModeType.PESSIMISTIC_WRITE, properties );
    }
    public List<Item> findAll(int offset,int limit){
        return em.createQuery("select i from Item i where i.status=:status", Item.class).setParameter("status", Item_status.SALE ).setFirstResult( offset ).setMaxResults( limit ).getResultList();
    }

    public List<Item> findAllbyType(String type,int offset,int limit){

        return em.createQuery("select i from Item i where i.DTYPE=:type and i.status=:status").setParameter("type",type).setParameter("status",Item_status.SALE).setFirstResult( offset ).setMaxResults( limit ).getResultList();

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
    @Modifying(clearAutomatically = true )
    public List<Item> findBytitleKeyword(String keyword,int offset,int limit){
        em.clear();
        return em.createQuery("select i from Item i where i.status=:status and (i.Name like concat('%',:keyword,'%'))").setParameter("status",Item_status.SALE).setParameter("keyword",keyword).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public int getCountofKeyword(String keyword){
        return em.createQuery("select i from Item i where i.status=:status and (i.Name like concat('%',:keyword,'%'))").setParameter("status",Item_status.SALE).setParameter("keyword",keyword).getResultList().size();
    }



    @Lock( LockModeType.PESSIMISTIC_WRITE )
    public Item pessimisticLockfind( Long id ){
        return em.find( Item.class, id );
    }


}
