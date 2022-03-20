package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Shoplist.Shop_Item;
import com.pipe09.OnlineShop.Dto.ShopCarts.ShopCartAppendDto;
import com.pipe09.OnlineShop.Dto.ShopCarts.ShopItemDto;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.ShopCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ShopCartApiController {
    private final ShopCartService cartService;
    private final MemberService memberService;
    private final ItemService itemService;

    @PostMapping("/api/v1/shopcarts/single/append.do")
    public boolean appendOnetoShopCart(@RequestBody @Valid ShopCartAppendDto dto){
        Long CartId=memberService.findById(dto.getUsername()).getShopCart().getShoplist_ID();
        Item item=itemService.findOne(dto.getItem_id());
        Boolean result=cartService.AppendNewItem(CartId,item,dto.getCount());
        return result;

    }

    @GetMapping("/api/v1/shopcarts/items/all")
    public List<ShopItemDto> getItemByUserName(@RequestParam String username){
        Long CartId=memberService.findById(username).getShopCart().getShoplist_ID();
        List<Shop_Item>itemList=cartService.getShopItems(CartId);
        List<ShopItemDto> dtoList=itemList.stream().map( ShopItemDto::new).collect(Collectors.toList());
        return dtoList;


    }
}
