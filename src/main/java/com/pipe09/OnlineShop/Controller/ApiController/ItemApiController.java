package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Dto.Item.ItemDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ItemApiController {
    private final ItemService service;

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

    @GetMapping("/api/v2/items/typed/{DTYPE}")
    public List<ItemDto> getminiItemsbyType(@PathVariable String DTYPE,@RequestParam int offset,@RequestParam int limit){
        List<Item>items=service.findAllbyType(DTYPE,offset,limit);
        DefaultMapper<ItemDto> mapper=new DefaultMapper<>(new ItemDto());
        List<ItemDto> dtoList=items.stream().map(item -> mapper.Translate(item)).collect(Collectors.toList());

        log.info("MiniItemListbyType:"+dtoList.toString());
        return dtoList;

    }
    @GetMapping("api/v2/items/cast-tool")
    public List<ItemDto> getminiItemsAboutTool(){
        List<Item>items=service.findAllaboutTools();
        DefaultMapper<ItemDto> mapper=new DefaultMapper<>(new ItemDto());
        List<ItemDto>dtoList=items.stream().map(item -> mapper.Translate(item)).collect(Collectors.toList());
        return dtoList;
    }
    /*
    @GetMapping("/api/v1/items/all")
    public List<ItemDto> getitemall(@RequestParam int offset, @RequestParam int limit){
        List<Item> list=service.findAll(offset,limit);
        List<ItemDto> dtoList=list.stream().map(ItemDto::new).collect(Collectors.toList());
        return dtoList;
    }

     */
    @GetMapping("/api/v2/items/all")
    public ResponseEntity<List<ItemDto>> getitemall( @RequestParam int offset, @RequestParam int limit ){
        try{
            // 아이템 불러오기
            List<Item> items=service.findAll(offset, limit);
            System.out.println( items.size() );
            HttpHeaders headers=new HttpHeaders();
            if( items.size() == 0 ){
                return new ResponseEntity<>( HttpStatus.NO_CONTENT );
            }

            /**
             * 쿠키 설정
             */
            /**
            int pageNum = ( offset / 12 ) + 1;

            //공백, 개행 안됨. CR/LF
            String val = "";



            for( int i = 0; i < items.size(); i++ ){
                val += items.get( i ).getImgSrc() + "~~~";
            }


            Cookie itemCookie = new Cookie( String.valueOf( pageNum ),val );
            //itemCookie.setSecure( true );
            itemCookie.setMaxAge( 600 );
            response.addCookie( itemCookie );
             */


            /**
             * 아이템 길이 밑 dtolist 반환
             */
            headers.set("itemLen",String.valueOf(items.get(0).getCountofItems()));
            DefaultMapper<ItemDto> mapper=new DefaultMapper<>(new ItemDto());
            List<ItemDto> dtoList=items.stream().map(item -> mapper.Translate(item)).collect(Collectors.toList());
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
    @GetMapping("/api/v2/items/single/{itemid}")
    public ItemDto getItembyId(@PathVariable Long itemid){
        Item item=service.findOne(itemid);
        DefaultMapper<ItemDto> mapper=new DefaultMapper<>(new ItemDto());
        return mapper.Translate(item);

    }
    @GetMapping("/api/v2/items/{keyword}")
    public ResponseEntity<List<ItemDto>> getItembykeyword(@RequestParam int offset,@RequestParam int limit,@PathVariable String keyword){
        try{
            Integer count=service.getcountofKeyword(keyword);
            HttpHeaders headers=new HttpHeaders();
            headers.set("count",count.toString());
            List<Item> items=service.findByTitleKeyword(keyword,offset,limit);
            DefaultMapper<ItemDto> mapper=new DefaultMapper<>(new ItemDto());
            List<ItemDto> dtoList=items.stream().map(item -> mapper.Translate(item)).collect(Collectors.toList());
            return new ResponseEntity(dtoList,headers,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
