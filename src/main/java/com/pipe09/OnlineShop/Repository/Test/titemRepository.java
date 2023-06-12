package com.pipe09.OnlineShop.Repository.Test;

import com.pipe09.OnlineShop.Domain.Item.V1.titem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@Repository
@RequiredArgsConstructor
public class titemRepository {

    private final EntityManager em;

    public Long save( titem item ){
        em.persist( item );

        return item.getItem_ID();
    }

    public titem findOne( Long id ){
        return em.find( titem.class, id );
    }
    @Lock( LockModeType.OPTIMISTIC )
    public titem findOneOpt( Long id ){
        return em.find( titem.class, id );
    }
}
