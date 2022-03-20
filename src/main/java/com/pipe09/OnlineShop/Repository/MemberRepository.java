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
    public Long joinByOauth(Member member){
        Member exist=this.findByNameWithOauth(member.getName());
        if(exist==null ){
            em.persist(member);

        }else{
            em.merge(member);
        }
        return member.getMember_ID();

    }
    public Long save(Member member){
        em.persist(member);

        return member.getMember_ID();
    }
    public Member findByNameWithOauth(String name){
        String jpql="select m from Member m where m.Name=:name";
        return em.createQuery(jpql,Member.class).setParameter("name",name).getSingleResult();
    }
    public Member findByName(String name){
        String jpql="select m from Member m join fetch m.shopCart where m.Name=:name and m.userType=:type";
        return em.createQuery(jpql,Member.class).setParameter("name",name).setParameter("type", UserType.LOCAL).getSingleResult();
    }
    public Member findByuserId(String id) {
        return em.createQuery("select m from Member m where m.user_ID=:id", Member.class)
                .setParameter("id", id).getSingleResult();
    }
    public List<Member> findAll(int offset,int limit){
        return em.createQuery("select mlist from Member mlist").setFirstResult(offset).setMaxResults(limit).getResultList();
    }



}
