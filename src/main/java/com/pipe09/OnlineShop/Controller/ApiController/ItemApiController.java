package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Dto.Item.ItemDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/api/v2/items/{DTYPE}")
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
    public List<ItemDto> getitemall(@RequestParam int offset, @RequestParam int limit){
        List<Item> items=service.findAll(offset, limit);
        DefaultMapper<ItemDto> mapper=new DefaultMapper<>(new ItemDto());
        List<ItemDto> dtoList=items.stream().map(item -> mapper.Translate(item)).collect(Collectors.toList());
        return dtoList;
    }

}
