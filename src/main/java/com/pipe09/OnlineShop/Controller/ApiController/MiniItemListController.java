package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Dto.M_ItemDto;
import com.pipe09.OnlineShop.Service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MiniItemListController {
    private final ItemService service;
    @GetMapping("/api/v1/m-item")
    public List<M_ItemDto> getminiItems(){
        List<Item>items=service.findAll();
        List<M_ItemDto> dtoList=Item.itemtoDto(items);
        log.info("MiniItemList :" + dtoList.toString());

        return dtoList;

    }
}
