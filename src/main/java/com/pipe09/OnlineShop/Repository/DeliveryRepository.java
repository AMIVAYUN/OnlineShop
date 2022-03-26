package com.pipe09.OnlineShop.Repository;

//import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
/*
@Repository
@RequiredArgsConstructor
public class DeliveryRepository {
    private final EntityManager em;
    public List<Delivery> findAll(int offset,int limit){
        return em.createQuery("select d from Delivery d join fetch d.order o",Delivery.class).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}


 */