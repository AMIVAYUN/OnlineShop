package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final EntityManager em;

    public void flush(){
        em.flush();
        return;
    }
    public Long save(Member member){
        em.persist(member);

        return member.getMember_ID();
    }
    /*
    public Member findByNameWithOauth(String name,UserType type){
        String jpql="select m from Member m where m.Name=:name and m.userType=:type";
        return em.createQuery(jpql,Member.class).setParameter("name",name).setParameter("type",type).getSingleResult();
    }
    public Member findByEmailWithOauth(String email,UserType type){
        String jpql="select m from Member m where m.email=:email and m.userType=:type";
        return em.createQuery(jpql,Member.class).setParameter("email",email).setParameter("type",type).getSingleResult();
    }

     */

    public Member findByName(String name){
        String jpql="select m from Member m join fetch m.shopCart where m.Name=:name and m.userType=:type";
        return em.createQuery(jpql,Member.class).setParameter("name",name).setParameter("type", UserType.LOCAL).getSingleResult();
    }
    public Member findByuserId(String id) {
        return em.createQuery("select m from Member m where m.user_ID=:id", Member.class)
                .setParameter("id", id).getSingleResult();
    }
    public List<Member> findByuserIdlist(String id){
        return em.createQuery("select m from Member m where m.user_ID=:id", Member.class)
                .setParameter("id", id).getResultList();
    }
    public List<Member> findAll(int offset,int limit){
        return em.createQuery("select mlist from Member mlist").setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    /*
    public Member findByEmailinOauth(String email){
        String jpql="select m from Member m where m.email=:email and m.userType<>:usertype";
        return em.createQuery(jpql,Member.class).setParameter("email",email).setParameter("usertype",UserType.LOCAL).getSingleResult();
    }

     */




}
