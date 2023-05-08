package com.pipe09.OnlineShop.Controller.RestController.v1_RestController;


import com.pipe09.OnlineShop.Domain.Member.Address;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.RoleType;
import com.pipe09.OnlineShop.Domain.Member.UserType;

import com.pipe09.OnlineShop.Dto.MemauthDto;
import com.pipe09.OnlineShop.Dto.Member.AddressDto;
import com.pipe09.OnlineShop.Dto.Member.MemberDto;
import com.pipe09.OnlineShop.Dto.Member.MemberManageDto;
import com.pipe09.OnlineShop.Dto.Member.SimpleStringDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.MemberService;
//import com.pipe09.OnlineShop.Service.Oauth2Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController{
    private final MemberService memberService;
    /*
    @GetMapping("/api/v1/members/is-oauth")
    public boolean isOauth(){
        SessionUser user=(SessionUser) oauth2Service.getHttpSession().getAttribute("user");
        if(user==null){
            return false;
        }else{
            return true;
        }


    }*/
    @ApiOperation( value = "로그 아웃" , notes = "LOGOUT" )
    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        log.info( auth.getName() + "님 로그아웃");
        if(auth!=null){
            new SecurityContextLogoutHandler().logout(request, response, auth);


        }
        return new RedirectView("/");
    }

    @ApiOperation( value = " 로그인 여부 확인하기 " , notes = " CHECK LOGIN " )
    @GetMapping("/api/v1/members/is-auth")
    public boolean isAuth(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @ApiOperation( value = " 로그인 유저 이름 반환하기 " , notes = " RETURN USERNAME " )
    @GetMapping("/api/v1/members/is-who")
    public String iswho(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @ApiOperation( value = " 유저 권한 확인하기 " , notes = " CHECK USER_AUTH " )
    @GetMapping("/api/v1/members/is-whom")
    public String iswhom(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
    }

    @ApiOperation( value = " SESSION 체크하기 " , notes = " CHECK SESSION " )
    @GetMapping("/api/v1/members/session")
    public ResponseEntity<MemauthDto> status(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        log.info( "Session checked " + auth.getName() + auth.getAuthorities().toString() );
        return new ResponseEntity<MemauthDto>(new MemauthDto(auth.isAuthenticated(),auth.getName(),auth.getAuthorities().toString()), HttpStatus.OK);



    }

    @ApiOperation( value = " offset, limit 기반 유저 정보 전체 반환하기 " , notes = " RETURN ALL MEMBERS " )
    @GetMapping("/api/v1/members/all")
    public List<MemberManageDto> getmemall(@RequestParam int offset, @RequestParam int limit){
        List<Member> list=memberService.findAll(offset,limit);
        List<MemberManageDto> dtoList=list.stream().map(MemberManageDto::new).collect(Collectors.toList());
        return dtoList;
    }

    @ApiOperation( value = " 중복 확인하기 " , notes = " CHECK DUPLICATE " )
    @GetMapping("/api/v1/checkdup")
    public ResponseEntity<Boolean> returning(@RequestParam String user_id){
        return new ResponseEntity<Boolean>(memberService.checkDup(user_id),HttpStatus.OK);
    }
    /*
    @GetMapping("/api/v1/members/single/local/{username}")
    public MemberDto getSingleMem(@PathVariable String username){
        Member member=memberService.findByname(username);
        MemberDto dto= new MemberDto(member.getUser_ID(),member.getPwd(),member.getEmail(),member.getName(),member.getPhone_Num());
        return dto;
    }

     */
    //TODO 보안취약 수정必 --> 수정 완 과거엔 아이디로 조회했기에 권한 여부를 떠나 타 아이디에 접근이 가능했음.

    @ApiOperation( value = " 유저 정보 확인하기 " , notes = " CHECK USER INFO " )
    @GetMapping("/api/v1/members/single/local/aboutMe")
    public MemberDto getSingleMembyID(){
        String user_id=SecurityContextHolder.getContext().getAuthentication().getName();;
        Member member= memberService.findById(user_id);
        MemberDto dto=new MemberDto(member.getUser_ID(),"접근 불가",member.getEmail(),member.getName(),member.getPhone_Num(),member.getAddress());
        return dto;
    }

    @ApiOperation( value = " 주소 업데이트 하기 " , notes = " UPDATE ADDRESS " )
    @PostMapping("/api/v1/members/updateAddress")
    public ResponseEntity<String>changeAddr(@RequestBody AddressDto dto,@AuthenticationPrincipal UserDetails details){
        try{
            String name=details.getUsername();
            if(name==null){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            memberService.UpdateAddress(name,dto);

            return new ResponseEntity("주소 변경에 성공하였습니다.",HttpStatus.OK);


        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity("주소 변경에 실패하였습니다.(자세 사항은 문의 부탁드립니다.)",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation( value = " 비밀번호 수정하기 " , notes = " UPDATE PWD " )
    @PostMapping("/api/v1/members/updatePwd")
    public ResponseEntity<String>changepwd(@RequestBody SimpleStringDto dto, @AuthenticationPrincipal UserDetails details){
        try{
            String name=details.getUsername();
            if(name==null){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            memberService.UpdatePwd(name,dto);

            return new ResponseEntity("비밀번호 변경에 성공하였습니다.",HttpStatus.OK);


        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity("비밀번호 변경에 실패하였습니다.(자세 사항은 문의 부탁드립니다.)",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation( value = " 연락처 수정하기 " , notes = " UPDATE PHONE_NUMBER " )
    @PostMapping("/api/v1/members/updatePhonenum")
    public ResponseEntity<String>changephone(@RequestBody SimpleStringDto dto, @AuthenticationPrincipal UserDetails details){
        try{

            String name=details.getUsername();
            if(name==null){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            memberService.UpdatePhone(name,dto);

            return new ResponseEntity("전화번호 변경에 성공하였습니다.",HttpStatus.OK);


        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity("전화번호 변경에 실패하였습니다.(자세 사항은 문의 부탁드립니다.)",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
