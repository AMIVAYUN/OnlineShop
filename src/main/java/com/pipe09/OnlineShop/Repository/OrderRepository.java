package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.OrderKey;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Utils.BASE64Utils;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Base64;
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
        String jpql="select o from Orders o where o.deliverystatus=:status";
        return em.createQuery(jpql,Orders.class).setParameter("status",status).getResultList();
    }
    public List<Orders> findAllwithToOne(int offset,int limit){
        return em.createQuery("select o from Orders o join fetch o.member m", Orders.class).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    public List<OrderItem> findOrderItems(Long id){
        try{
            return em.createQuery("select oi from OrderItem oi join fetch oi.item i where oi.orders.Order_ID=:id",OrderItem.class).setParameter("id",id).getResultList();
        }catch (EntityNotFoundException e){
            return null;
        }

    }
    /*
    public String saveOrderKey(String key,Long Order_ID){
        OrderKey orderKey=new OrderKey();
        orderKey.setOrderKey(key); orderKey.setOrder_ID(Order_ID);
        em.persist(orderKey);
        return orderKey.getOrderKey();
    }

     */
    

}
