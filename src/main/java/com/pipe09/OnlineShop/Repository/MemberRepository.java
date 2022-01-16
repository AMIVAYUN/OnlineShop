package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Member.Member;
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

    public Long save(Member member){
        em.persist(member);

        return member.getMember_ID();
    }
    public List<Member> findByName(String name){
        String jpql="select m from Member m where m.Name=:name";
        return em.createQuery(jpql,Member.class).setParameter("name",name).getResultList();
    }
    public Member findById(String id){
        return em.find(Member.class,id);
    }
    public List<Member> findAll(){
        return em.createQuery("select mlist from Member mlist").getResultList();
    }


}
