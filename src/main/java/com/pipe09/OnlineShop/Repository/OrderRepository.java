package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public Long save(Orders order){
        em.persist(order);
        return order.getOrder_ID();
    }
    public Orders findOne(Long id){
        return em.find(Orders.class,id);
    }
    public List<Orders> findAll(){
        String jpql="select o from Orders o";
        return em.createQuery(jpql).getResultList();
    }
    public List<Orders> findByDeliveryStatus(Deliverystatus status){
        String jpql="select o from Orders o join fetch o.delivery where o.delivery.status=:status";
        return em.createQuery(jpql,Orders.class).setParameter("status",status).getResultList();
    }

}
