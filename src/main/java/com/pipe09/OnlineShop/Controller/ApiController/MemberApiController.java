package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.UserType;
import com.pipe09.OnlineShop.Domain.SessionUser;
import com.pipe09.OnlineShop.Dto.MemauthDto;
import com.pipe09.OnlineShop.Dto.Member.MemberDto;
import com.pipe09.OnlineShop.Dto.Member.MemberManageDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController{
    private final MemberService memberService;
    private final HttpSession session;
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
        SessionUser user=(SessionUser) session.getAttribute("user");
        if(user==null){
            return new ResponseEntity<MemauthDto>(new MemauthDto(auth.isAuthenticated(), auth.getName(), auth.getAuthorities().toString()), HttpStatus.OK);
        }else{
            return new ResponseEntity<MemauthDto>(new MemauthDto(auth.isAuthenticated(), user.getName(), auth.getAuthorities().toString()), HttpStatus.OK);
        }

    }
    @GetMapping("/api/v1/members/all")
    public List<MemberManageDto> getmemall(@RequestParam int offset, @RequestParam int limit){
        List<Member> list=memberService.findAll(offset,limit);
        List<MemberManageDto> dtoList=list.stream().map(MemberManageDto::new).collect(Collectors.toList());
        return dtoList;
    }
    @PostMapping("/join-proc")
    public String create(@Valid MemberDto dto){
        Member newMember= Member.createMember(dto.getUsername(),dto.getName(),dto.getPhone_num(),dto.getPassword(),dto.getEmail()) ;
        memberService.save(newMember);
        return "redirect:/";
    }
    /*
    @GetMapping("/api/v1/members/single/local/{username}")
    public MemberDto getSingleMem(@PathVariable String username){
        Member member=memberService.findByname(username);
        MemberDto dto= new MemberDto(member.getUser_ID(),member.getPwd(),member.getEmail(),member.getName(),member.getPhone_Num());
        return dto;
    }

     */
    @GetMapping("/api/v1/members/single/local/{user_id}")
    public MemberDto getSingleMembyID(@PathVariable String user_id){
        Member member= memberService.findById(user_id);
        MemberDto dto=new MemberDto(member.getUser_ID(),"발신거부",member.getEmail(),member.getName(),member.getPhone_Num());
        return dto;
    }
}
