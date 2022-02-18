package com.pipe09.OnlineShop.Controller;

import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Dto.Member.MemberDto;
import com.pipe09.OnlineShop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    @GetMapping("/login")
    public String login(){
        return "fragments/public/login";
    }



    @GetMapping("/join")
    public String join(){
        return "fragments/public/join";
    }
    @PostMapping("/join-proc")
    public String create(@Valid MemberDto dto){
        System.out.println(dto.getEmail());
        Member newMember= Member.createMember(dto.getUsername(),dto.getName(),dto.getPhone_num(),dto.getPassword(),dto.getEmail()) ;
        service.save(newMember);
        return "redirect:/";
    }
}
