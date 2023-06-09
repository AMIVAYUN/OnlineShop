package com.pipe09.OnlineShop.Controller.RestController.v2_RestController;


import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import com.pipe09.OnlineShop.Dto.Item.V1.ItemDto;
import com.pipe09.OnlineShop.Service.Itemv2Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ItemApiController {
    private final Itemv2Service service;




    @ApiOperation( value = "타입, offset, limit 별 아이템 불러오기" , notes = "LOAD TYPE,KEYWORD,OFFSET & LIMIT BASED ITEM" )
    @GetMapping("/api/v2/items/typed/{DTYPE}")
    public ResponseEntity< List<ItemDto> > getminiItemsbyType(@PathVariable String DTYPE,@RequestParam int offset,@RequestParam int limit){
        try{
            List<Itemv2>items=service.findAllbyType(DTYPE,offset,limit);

            List<ItemDto> dtoList=items.stream().map( ItemDto::new ).collect(Collectors.toList());

            log.info( SecurityContextHolder.getContext().getAuthentication().getName() + "님께서 타입 별 불러오기 실행" + "MiniItemListbyType:"+dtoList.toString());

            return new ResponseEntity< List<ItemDto> >( dtoList,new HttpHeaders(),HttpStatus.OK );
        }catch( Exception e ){
            log.info( SecurityContextHolder.getContext().getAuthentication().getName() + "님께서 타입 별 불러오기 간 에러 발생" );
        }
        return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );


    }
    /*
    @ApiOperation( value = " 아이템 전체 불러오기 ", notes = " LOAD ALL ITEMS")
    @GetMapping("/api/v2/items")
    public ResponseEntity< List< ItemDto > > getItemAll(){
        try{
            List<ItemDto> dtoList = service.findAll().stream().map(ItemDto::new).collect(Collectors.toList());
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity< List<ItemDto> >( dtoList,headers,HttpStatus.OK );

        }catch( Exception e ){
            log.info(SecurityContextHolder.getContext() + "님께서 아이템 로딩간 에러 발생" );
            log.info( e.toString() );
        }
        return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );

    }



     */
    @ApiOperation( value = " 아이템 전체 || offset, limit 기반 아이템 불러오기" , notes = "LOAD OFFSET & LIMIT BASED ITEM" )
    @GetMapping("/api/v2/items")
    public ResponseEntity<List<ItemDto>> getitemall(@RequestParam( required = false ) Integer offset, @RequestParam( required = false ) Integer limit ){
        try{

            List<Itemv2> items =( offset == null ? service.findAll() : service.findAllWithOffLim( offset, limit ) );
            // 아이템 불러오기
            HttpHeaders headers=new HttpHeaders();
            if( items.size() == 0 ){
                return new ResponseEntity<>( HttpStatus.NOT_FOUND );
            }


            headers.set("itemLen",String.valueOf(items.get(0).getCountofItems()));
            List<ItemDto> dtoList=items.stream().map( ItemDto::new ).collect(Collectors.toList());

            return new ResponseEntity(dtoList,headers, HttpStatus.OK);
        }catch (Exception e){
            log.info(this.getClass().getName()+e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation( value = "아이템 ID로 해당 아이템 조회" , notes = "LOAD SINGLE ITEM WITH ID" )
    @GetMapping("/api/v2/items/{itemid}")
    public ResponseEntity < ItemDto > getItembyId(@PathVariable Long itemid){
        try{
            Itemv2 item=service.findOne(itemid);
            log.info( SecurityContextHolder.getContext().getAuthentication().getName() + "님 께서 " + itemid + "조회" );
            return new ResponseEntity( new ItemDto( item ), new HttpHeaders(), HttpStatus.OK );
        }catch( Exception e ){
            log.info( SecurityContextHolder.getContext().getAuthentication().getName() + "님 께서 " + itemid + "조회간 오류 발생" );
        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST );

    }

    @ApiOperation( value = "offset & limit, 키워드로 아이템 조회 " , notes = "LOAD ITEMS WITH KEYWORD, OFFSET & LIMIT" )
    @GetMapping("/api/v2/items/keyword/{keyword}")
    public ResponseEntity<List<ItemDto>> getItembykeyword(@RequestParam int offset,@RequestParam int limit,@PathVariable String keyword){
        try{
            Integer count=service.getcountofKeyword(keyword);
            HttpHeaders headers=new HttpHeaders();
            headers.set("count",count.toString());
            List<Itemv2> items=service.findByTitleKeyword(keyword,offset,limit);
            List<ItemDto> dtoList=items.stream().map( ItemDto::new ).collect(Collectors.toList());
            return new ResponseEntity(dtoList,headers,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
