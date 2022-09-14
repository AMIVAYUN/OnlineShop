package com.pipe09.OnlineShop.Repository;


import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType_Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DtypeRepository {
    private final EntityManager em;


    // 저장
    public Long save(dType dtype){

        em.persist( dtype );

        return dtype.getId();
    }


    public dType findItem( Long id ){
        dType fType = em.find( dType.class, id );
        return fType;
    }

    public dType findByname( String name ){
        dType fType = em.createQuery( "select dt from dType dt where dt.name = :name ", dType.class ).setParameter("name", name ).getSingleResult();
        return fType;
    }

    //삭제


    public String delete( String name ){
        dType fType = em.find( dType.class, name );
        fType.setDTypeStatus( dType_Status.DELETED );
        return fType.getName();
    }

    public List< dType > findAll( int offset, int limit ){
        List< dType > lst = em.createQuery("select dt from dType dt where dt.dTypeStatus = :status " ).setFirstResult( offset ).setMaxResults( limit ).getResultList();

        return lst;
    }

    public Long findClassifierId( String name ){
        Long id = em.createQuery( "select dt from dType dt where dt.name =:tools ", com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType.class).setParameter("tools", name ).getSingleResult().getId();
        return id;
    }

}
