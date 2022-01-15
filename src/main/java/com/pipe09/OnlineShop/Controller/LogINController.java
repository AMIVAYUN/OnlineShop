package com.pipe09.OnlineShop.Controller;

import com.pipe09.OnlineShop.Repository.MemberRepository;
import com.pipe09.OnlineShop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LogINController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String Login(){

        return "login";
    }
}
