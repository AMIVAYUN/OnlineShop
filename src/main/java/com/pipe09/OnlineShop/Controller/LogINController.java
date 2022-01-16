package com.pipe09.OnlineShop.Controller;

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

        return "MemberShip/login";
    }
    @GetMapping("/newMember")
    public String Enter_NewMember(){
        return "MemberShip/newMember";
    }
}
