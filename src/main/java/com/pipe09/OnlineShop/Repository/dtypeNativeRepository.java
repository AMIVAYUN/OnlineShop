package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface dtypeNativeRepository extends JpaRepository<Itemv2, Long > {
    /**
    @Query( value = " select id from dType ",nativeQuery = true )
    public Long findChildsAboutClassifier( String name ){

    }
    **/


}
