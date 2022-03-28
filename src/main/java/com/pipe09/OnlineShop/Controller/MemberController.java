package com.pipe09.OnlineShop.Controller;

import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Dto.Member.MemberDto;
import com.pipe09.OnlineShop.Service.MailService;
import com.pipe09.OnlineShop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;
    @GetMapping("/login")
    public String login(HttpServletRequest request)
    {
        Cookie[] cookies= request.getCookies();
        return "fragments/public/login";
    }



    @GetMapping("/join")
    public String join(){
        return "fragments/public/join1";
    }


    @GetMapping("/mypage")
    public String getMypage(){
        return "fragments/public/mypage";
    }


}
