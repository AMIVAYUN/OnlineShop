package com.pipe09.OnlineShop.Repository;



import com.pipe09.OnlineShop.Domain.Item.V1.Item_status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class ItemV2Repository {
    private final EntityManager em;
    private final DtypeRepository dtypeRepository;

    public Long save(Itemv2 item) {
        if (item.getItem_ID() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }

        return item.getItem_ID();
    }

    //앞으론 까먹지말고 매 레포지토리에 적용하도록 하자.
    public void clear() {
        em.clear();
    }

    public void flush() {
        em.flush();
    }

    public Itemv2 findItem(Long id) {
        return em.find(Itemv2.class, id);
    }

    public List<Itemv2> findAll(int offset, int limit) {
        String jpql = "select i from Itemv2 i where i.status=:status";
        return em.createQuery(jpql, Itemv2.class).setParameter("status", Item_status.SALE).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Itemv2> findAllbyType(String type, int offset, int limit) {

        return em.createQuery("select i from Itemv2 i join fetch i.dType where i.status=:status and i.dType.name = :type").setParameter("type", type).setParameter("status", Item_status.SALE).setFirstResult(offset).setMaxResults(limit).getResultList();

    }


    public boolean removeById(Long id) {
        Itemv2 item = em.find(Itemv2.class, id);
        if (item == null) {
            return false;
        }
        item.setStatus(Item_status.DELETED);
        em.flush();
        return true;
    }



    public List<Itemv2> findBytitleKeyword(String keyword, int offset, int limit) {
        em.clear();
        return em.createQuery("select i from Itemv2 i where i.status=:status and (i.name like concat('%',:keyword,'%'))").setParameter("status", Item_status.SALE).setParameter("keyword", keyword).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public int getCountofKeyword(String keyword) {

        return em.createQuery("select i from Itemv2 i where i.status=:status and (i.name like concat('%',:keyword,'%'))").setParameter("status", Item_status.SALE).setParameter("keyword", keyword).getResultList().size();

    }

    public List<Itemv2> findAllaboutTools() {

        return em.createQuery( "select i from Itemv2 i where i.dType.name =: name").setParameter( "name", "Tools").getResultList();
    }
}
