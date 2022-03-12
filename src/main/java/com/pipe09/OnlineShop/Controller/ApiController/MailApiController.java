package com.pipe09.OnlineShop.Controller.ApiController;

import com.pipe09.OnlineShop.Dto.Mail.MailDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
public class MailApiController {
    private final MailService service;

    @PostMapping("/api/v2/mails/post.do")
    public String SendMail(@RequestBody MailDto dto){
        log.info(dto.getName()+"유저 메일 서비스 사용");
        service.sendMail(service.makemail(dto));
        return "전송에 성공하였습니다";
    }
}
