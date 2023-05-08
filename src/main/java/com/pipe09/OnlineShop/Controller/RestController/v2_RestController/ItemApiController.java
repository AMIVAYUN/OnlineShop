package com.pipe09.OnlineShop.Controller.RestController.v2_RestController;


import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import com.pipe09.OnlineShop.Dto.Item.ItemDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.Itemv2Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<ItemDto> getminiItemsbyType(@PathVariable String DTYPE,@RequestParam int offset,@RequestParam int limit){
        List<Itemv2>items=service.findAllbyType(DTYPE,offset,limit);

        List<ItemDto> dtoList=items.stream().map( ItemDto::new ).collect(Collectors.toList());

        log.info("MiniItemListbyType:"+dtoList.toString());
        return dtoList;

    }
    @ApiOperation( value = " 공구 불러오기 " , notes = "LOAD TOOLS" )
    @GetMapping("api/v2/items/cast-tool")
    public List<ItemDto> getminiItemsAboutTool(){
        List<Itemv2>items=service.findAllaboutTools();
        List<ItemDto>dtoList=items.stream().map( ItemDto::new ).collect(Collectors.toList());
        return dtoList;
    }



    @ApiOperation( value = "offset, limit 기반 아이템 불러오기" , notes = "LOAD OFFSET & LIMIT BASED ITEM" )
    @GetMapping("/api/v2/items/all")
    public ResponseEntity<List<ItemDto>> getitemall( @RequestParam int offset, @RequestParam int limit ){
        try{
            // 아이템 불러오기
            List<Itemv2> items=service.findAll(offset, limit);
            HttpHeaders headers=new HttpHeaders();
            if( items.size() == 0 ){
                return new ResponseEntity<>( HttpStatus.NOT_FOUND );
            }


            headers.set("itemLen",String.valueOf(items.get(0).getCountofItems()));
            List<ItemDto> dtoList=items.stream().map( ItemDto::new ).collect(Collectors.toList());
            /*
            CacheControl cache = CacheControl.maxAge( 600, TimeUnit.SECONDS ).noTransform().mustRevalidate();
            headers.setCacheControl( cache );

             */
            //return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60,TimeUnit.SECONDS)).headers(headers).body(dtoList);
            return new ResponseEntity(dtoList,headers, HttpStatus.OK);
        }catch (Exception e){
            log.info(this.getClass().getName()+e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @ApiOperation( value = "아이템 ID로 해당 아이템 조회" , notes = "LOAD SINGLE ITEM WITH ID" )
    @GetMapping("/api/v2/items/single/{itemid}")
    public ItemDto getItembyId(@PathVariable Long itemid){
        Itemv2 item=service.findOne(itemid);

        return new ItemDto( item );

    }
    @ApiOperation( value = "offset & limit, 키워드로 아이템 조회 " , notes = "LOAD ITEMS WITH KEYWORD, OFFSET & LIMIT" )
    @GetMapping("/api/v2/items/{keyword}")
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

    /*
    @GetMapping("/api/v1/m-item")
    public List<M_ItemDto> getminiItems(@RequestParam int offset,@RequestParam int limit){
        List<Item>items=service.findAll(offset,limit);
        //List<M_ItemDto> dtoList=Item.itemtoDto(items);
        log.info("MiniItemList :" + dtoList.toString());

        return dtoList;

    }

     */


    /*
    @GetMapping("/api/v1/m-item/{DTYPE}")
    public List<M_ItemDto> getminiItemsbyType(@PathVariable String DTYPE,@RequestParam int offset,@RequestParam int limit){
        List<Item>items=service.findAllbyType(DTYPE,offset,limit);
        List<M_ItemDto>dtoList=Item.itemtoDto(items);

        log.info("MiniItemListbyType:"+dtoList.toString());
        return dtoList;

    }

    @GetMapping("api/v1/m-item/cast-tool")
    public List<M_ItemDto> getminiItemsAboutTool(){
        List<Item>items=service.findAllaboutTools();
        List<M_ItemDto>dtoList=Item.itemtoDto(items);
        return dtoList;
    }

     */
    /*
    @GetMapping("/api/v1/items/all")
    public List<ItemDto> getitemall(@RequestParam int offset, @RequestParam int limit){
        List<Item> list=service.findAll(offset,limit);
        List<ItemDto> dtoList=list.stream().map(ItemDto::new).collect(Collectors.toList());
        return dtoList;
    }

     */

}
