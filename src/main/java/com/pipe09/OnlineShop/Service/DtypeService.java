package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType_Status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dtype_classify;
import com.pipe09.OnlineShop.Repository.DtypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DtypeService {
    private final DtypeRepository repository;


    @Transactional
    public Long createDtype(String name ){

        dType newdType = new dType( name );
        Long id = repository.save( newdType );
        return id;


    }

    @Transactional
    public List<dType> findAll(){

        List<dType> dTypeList = repository.findAll();

        return dTypeList;

    }

    @Transactional
    public dType findByName( String name ){
        return repository.findByname( name );
    }
}
