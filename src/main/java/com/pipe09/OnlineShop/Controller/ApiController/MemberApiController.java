package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Dto.MemauthDto;
import com.pipe09.OnlineShop.Dto.Member.MemberManageDto;
import com.pipe09.OnlineShop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController{
    private final MemberService memberService;
    @GetMapping("/api/v1/members/is-auth")
    public boolean isAuth(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
    @GetMapping("/api/v1/members/is-who")
    public String iswho(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    @GetMapping("/api/v1/members/is-whom")
    public String iswhom(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
    }
    @GetMapping("/api/v1/members/session")
    public ResponseEntity<MemauthDto> status(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<MemauthDto>(new MemauthDto(auth.isAuthenticated(),auth.getName(),auth.getAuthorities().toString()), HttpStatus.OK);
    }
    @GetMapping("/api/v1/members/all")
    public List<MemberManageDto> getmemall(@RequestParam int offset, @RequestParam int limit){
        List<Member> list=memberService.findAll(offset,limit);
        List<MemberManageDto> dtoList=list.stream().map(MemberManageDto::new).collect(Collectors.toList());
        return dtoList;
    }
}
