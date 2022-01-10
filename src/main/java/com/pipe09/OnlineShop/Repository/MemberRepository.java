package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getMember_ID();
    }
    public Member findByName(String name){
        String jpql="select m from Member m where m.Name=:name";
        return em.createQuery(jpql,Member.class).setParameter("name",name).getSingleResult();
    }
    public Member findById(Long id){
        return em.find(Member.class,id);
    }
    public List<Member> findAll(){
        return em.createQuery("select mlist from Member mlist").getResultList();
    }


}
