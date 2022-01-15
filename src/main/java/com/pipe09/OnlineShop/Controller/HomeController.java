package com.pipe09.OnlineShop.Controller;


import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final ItemService itemService;
    @GetMapping("/")
    public String home(Model model){
        List<Item> items=itemService.findAll();
        model.addAttribute("items",items);
        log.info(items.toString());
        return "home";
    }
}
