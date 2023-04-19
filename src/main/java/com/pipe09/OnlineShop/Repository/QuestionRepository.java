package com.pipe09.OnlineShop.Repository;


import com.pipe09.OnlineShop.Domain.Question.V1.questionV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class QuestionRepository {
    private final EntityManager em;

    public Long save(questionV1 qv1 ){

        em.persist( qv1 );

        return qv1.getQuestion_Id();
    }

    public void clear(){
        em.clear();
    }
    public void flush(){
        em.flush();
    }
    // 이름으로 찾기
    public List< questionV1 > findlstByuser( Long member_id ){
        return em.createQuery( "select qV1 from questionV1 qV1 where qV1.member.Member_ID =: member_id").setParameter( "member_id", member_id ).getResultList();
    }

    // 전부 찾기

    public List< questionV1 > findAll( Long member_id ){
        return em.createQuery("select qV1 from questionV1 qV1 ").getResultList();
    }


}
