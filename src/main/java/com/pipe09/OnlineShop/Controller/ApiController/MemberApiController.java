package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.RoleType;
import com.pipe09.OnlineShop.Domain.Member.UserType;
import com.pipe09.OnlineShop.Domain.SessionUser;
import com.pipe09.OnlineShop.Dto.MemauthDto;
import com.pipe09.OnlineShop.Dto.Member.MemberDto;
import com.pipe09.OnlineShop.Dto.Member.MemberManageDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.Oauth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
    private final Oauth2Service oauth2Service;
    @GetMapping("/api/v1/members/is-oauth")
    public boolean isOauth(){
        SessionUser user=(SessionUser) oauth2Service.getHttpSession().getAttribute("user");
        if(user==null){
            return false;
        }else{
            return true;
        }
    }
    @GetMapping("/api/v1/members/is-auth")
    public boolean isAuth(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
    @GetMapping("/api/v1/members/is-who")
    public String iswho(){
        SessionUser user=(SessionUser) oauth2Service.getHttpSession().getAttribute("user");
        if(user==null){
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }else{
            return user.getEmail();
        }

    }
    @GetMapping("/api/v1/members/is-whom")
    public String iswhom(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
    }
    @GetMapping("/api/v1/members/session")
    public ResponseEntity<MemauthDto> status(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();

        SessionUser user=(SessionUser) oauth2Service.getHttpSession().getAttribute("user");
        if(user==null){
            return new ResponseEntity<MemauthDto>(new MemauthDto(auth.isAuthenticated(),auth.getName(),auth.getAuthorities().toString()), HttpStatus.OK);
        }else{
            return new ResponseEntity<MemauthDto>(new MemauthDto(auth.isAuthenticated(), user.getEmail(), "ROLE_USER"), HttpStatus.OK);
        }

    }
    @GetMapping("/api/v1/members/all")
    public List<MemberManageDto> getmemall(@RequestParam int offset, @RequestParam int limit){
        List<Member> list=memberService.findAll(offset,limit);
        List<MemberManageDto> dtoList=list.stream().map(MemberManageDto::new).collect(Collectors.toList());
        return dtoList;
    }
    @PostMapping("/join-proc")
    public boolean create(@Valid @RequestBody MemberDto dto){
        log.info(dto.getAddress().getAddress());
        Member newMember= Member.createMember(dto.getUsername(),dto.getName(),dto.getPhone_num(),dto.getPassword(),dto.getEmail(),dto.getAddress()) ;
        memberService.save(newMember);
        return true;
    }
    /*
    @GetMapping("/api/v1/members/single/local/{username}")
    public MemberDto getSingleMem(@PathVariable String username){
        Member member=memberService.findByname(username);
        MemberDto dto= new MemberDto(member.getUser_ID(),member.getPwd(),member.getEmail(),member.getName(),member.getPhone_Num());
        return dto;
    }

     */
    //TODO 보안취약 수정必 --> 수정 완
    @GetMapping("/api/v1/members/single/local/aboutMe")
    public MemberDto getSingleMembyID(){
        SessionUser user=(SessionUser) oauth2Service.getHttpSession().getAttribute("user");
        String user_id;
        if(user==null){
            user_id=SecurityContextHolder.getContext().getAuthentication().getName();
        }else{
            user_id= user.getEmail();
        }
        Member member= memberService.findById(user_id);
        MemberDto dto=new MemberDto(member.getUser_ID(),"접근 불가",member.getEmail(),member.getName(),member.getPhone_Num(),member.getAddress());
        return dto;
    }
}
