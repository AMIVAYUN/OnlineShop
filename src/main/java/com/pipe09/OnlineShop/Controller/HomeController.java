package com.pipe09.OnlineShop.Controller;


import com.pipe09.OnlineShop.Service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final ItemService itemService;
    @GetMapping("/")
    public String home(Model model) {
        log.info("home access");
        return "fragments/public/home";
    }




}
