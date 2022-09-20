package com.pipe09.OnlineShop.Controller;

import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Dto.Member.MemberDto;
import com.pipe09.OnlineShop.Service.MailService;
import com.pipe09.OnlineShop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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


    /*
    @GetMapping("/join")
    public String join(){
        return "fragments/public/join1";
    }


     */

    @GetMapping("/mypage")
    public String getMypage(){
        return "fragments/private/mypagev2";
    }


    /*
    @PostMapping("/join-proc")
    public ResponseEntity<String> create(@Valid @RequestBody MemberDto dto){
        log.info(dto.getAddress().getAddress());
        Member newMember= Member.createMember(dto.getUsername(),dto.getName(),dto.getPhone_num(),dto.getPassword(),dto.getEmail(),dto.getAddress()) ;
        service.save(newMember);
        return new ResponseEntity<>("저장에 성공하였습니다", HttpStatus.OK);
    }

     */

    /*
    @PostMapping("/loginproc")
    public String login(@Valid MemberDto dto){
        log.info(dto.getUsername()+"로그인 시도");
        service.loadUserByUsername(dto.getUsername());
        return "redirect:/";
    }

     */
}
