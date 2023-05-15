package com.pipe09.OnlineShop.Controller.RestController.v2_RestController;


import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Dto.dType.dTypeDto;
import com.pipe09.OnlineShop.Service.DtypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.PersistenceException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DtypeApiController {

    private final DtypeService service;

    @GetMapping("/api/v2/dtype")
    public ResponseEntity<List<dTypeDto>> getdTypeList(){
        try{

            List<dTypeDto> target = service.findAll().stream().map( dTypeDto::new ).collect(Collectors.toList());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType( new MediaType( "application", "json", StandardCharsets.UTF_8 ) );
            return new ResponseEntity( target, headers, HttpStatus.OK );
        }catch( Exception e ){
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

}
