package com.pipe09.OnlineShop.Repository;


import com.pipe09.OnlineShop.Domain.Board.Notice;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;
    public Long save(Notice notice){
        em.persist(notice);
        return notice.getNotice_ID();
    }
    public List<Notice> readNoticeAll(){
        return em.createQuery("select nlist from Notice nlist").getResultList();
    }
    public Notice findByID(Long id){ return em.find(Notice.class,id);}
    public boolean removeNoticeByID(Long id) throws JpaSystemException {
        Notice notice=em.find(Notice.class,id);
        if(notice==null){
            return false;
        }
        em.remove(notice);
        return true;
    }

}
