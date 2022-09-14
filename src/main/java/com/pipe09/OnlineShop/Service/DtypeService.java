package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType_Status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dtype_classify;
import com.pipe09.OnlineShop.Repository.DtypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DtypeService {
    private final DtypeRepository repository;


    @Transactional
    public Long createDtype(String name, Integer optionNum, String parentName, dtype_classify classifier){
        dType newdType;
        Long id;
        switch ( optionNum ){
            default:
                dType pdType = repository.findByname( parentName );
                newdType = new dType( name, pdType, classifier );
                id = repository.save( newdType );
                return id;
            case 0:
                newdType = new dType( name );
                id = repository.save( newdType );
                return id;

        }
    }
}
