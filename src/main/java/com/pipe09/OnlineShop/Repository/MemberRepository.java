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
    public Long joinByOauth(Member member){
        List<Member> exist=this.findByName(member.getName());
        if(exist==null || exist.isEmpty() ){
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

    public List<Member> findByName(String name){
        String jpql="select m from Member m where m.Name=:name";
        return em.createQuery(jpql,Member.class).setParameter("name",name).getResultList();
    }
    public Member findByuserId(String id) {
        return em.createQuery("select m from Member m where m.user_ID=:id", Member.class)
                .setParameter("id", id).getSingleResult();
    }
    public List<Member> findAll(int offset,int limit){
        return em.createQuery("select mlist from Member mlist").setFirstResult(offset).setMaxResults(limit).getResultList();
    }


}
