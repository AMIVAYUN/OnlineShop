package com.pipe09.OnlineShop.Repository;


import com.pipe09.OnlineShop.Domain.Payment.payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    private final EntityManager em;

    public String save(payment payment){
        em.persist(payment);
        return payment.getPaymentKey();
    }

    public payment findByOrderId(String OrderId){
        try{
            return em.createQuery("select p from payment p where p.orderId=:orderId",payment.class).setParameter("orderId",OrderId).getSingleResult();
        }catch(NoResultException e){
            return new payment(true);
        }
    }
    public List<payment> findByUserId(String user_id, int offset, int limit){
        try{
            return em.createQuery("select p from payment p join fetch p.member where p.member.user_ID=:user_id").setParameter("user_id",user_id).setFirstResult(offset).setMaxResults(limit).getResultList();
        }catch(EntityNotFoundException e){
            return null;
        }

    }

}
