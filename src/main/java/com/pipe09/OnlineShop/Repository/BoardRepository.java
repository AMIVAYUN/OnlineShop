package com.pipe09.OnlineShop.Repository;


import com.pipe09.OnlineShop.Domain.Board.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;
    public Long save(Notice notice){
        em.persist(notice);
        return notice.getNotice_ID();
    }
    public List<Notice> readNoticeAll(){
        return em.createQuery("select nlist from Notice nlist").getResultList();
    }
    public void removeNoticeByID(Long id){
        Notice notice=em.find(Notice.class,id);
        em.remove(notice);

    }
}
