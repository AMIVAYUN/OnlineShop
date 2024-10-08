package com.pipe09.OnlineShop.Controller.RestController.v1_RestController;

import com.pipe09.OnlineShop.Dto.Mail.MProveDto;
import com.pipe09.OnlineShop.Dto.Mail.MailDto;
import com.pipe09.OnlineShop.Dto.Mail.MailProveDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.MailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailApiController {
    private final MailService service;


    @ApiOperation( value = "메일 쓰기" , notes = "WRITE MAIL API" )
    @PostMapping("/api/v2/mails/post.do")
    public ResponseEntity SendMail(@RequestBody MailDto dto){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(username+"유저 메일 서비스 사용");
        service.makeQuestionAndSend(dto,username);
        return new ResponseEntity( HttpStatus.OK );
    }
    @ApiOperation( value = "메일로 인증하기" , notes = "DO AUTHENTICATION BY EMAIL API" )
    @GetMapping("/api/v2/mails/confirm")
    public ResponseEntity StartProve(@RequestParam String email) {
        log.info(email+"유저 이메일 인증 서비스 진행");
        try{
            service.Emailprove(email);

            return new ResponseEntity( HttpStatus.OK );
        }catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @ApiOperation( value = "인증 결과 반환하기" , notes = "RETURN AUTHENTICATION RESULT API" )
    @PostMapping("/api/v2/mails/prove.do")
    public MailProveDto GetProve(@RequestBody MProveDto dto){
        MailProveDto result= new MailProveDto();
        result.setResult(service.confirm(dto));
        return result;
    }
}
