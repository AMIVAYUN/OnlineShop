package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Dto.Mail.MProveDto;
import com.pipe09.OnlineShop.Dto.Mail.MailDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender sender;
    private final SpringTemplateEngine engine;
    @Getter
    private HashMap<String,String> authfield=new HashMap<>();
    @Async
    public SimpleMailMessage makeQuestion(MailDto dto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("wntjrdbs@gmail.com");
        message.setSubject(dto.getName()+"님께서 보내주신 문의입니다.");
        message.setText("보낸 날짜: "+dto.getWrittendate()+"\n"+"연락처: "+dto.getPhonenum()+"\n"+"내용: "+dto.getContent());
        return message;


    }

    public boolean confirm(MProveDto dto){

        return (authfield.get(dto.getEmail())).equals(dto.getKey());

    }

    @Async
    public void Emailprove(String To)throws MessagingException, IOException {
        MimeMessage message=sender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message,true);
        helper.setSubject("대봉 산업기계 이메일 인증 코드입니다.");
        helper.setTo(To);

        Context context=new Context();
        HashMap<String,String> map=new HashMap<>();
        map.put("code",getAuthkey(To));
        map.forEach((key,value) ->{
            System.out.println(key+" "+value);
            context.setVariable(key,value);
        });
        String html=engine.process("fragments/private/MailIdentify",context);
        helper.setText(html,true);

        sender.send(message);


    }
    @Async
    public String getAuthkey(String to){
        Random rand=new Random();
        String key=String.valueOf(rand.nextInt(999999));
        this.authfield.put(to,key);
        return key;
    }


    @Async
    public void sendMail(SimpleMailMessage message){
        //ArrayList<String> toUserList=new ArrayList<>();
        /*
        toUserList.add("wntjrdbs@gmail.com");
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(toUserList.get(0));
        message.setFrom("user");
        message.setSubject("test");
        message.setText("테스트입니다");

         */
        sender.send(message);
    }

}
