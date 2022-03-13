package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Dto.Mail.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender sender;
    public SimpleMailMessage makemail(MailDto dto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("wntjrdbs@gmail.com");
        message.setSubject(dto.getName()+"님께서 보내주신 문의입니다.");
        message.setText("보낸 날짜: "+dto.getWrittendate()+"\n"+"연락처: "+dto.getPhonenum()+"\n"+"내용: "+dto.getContent());
        return message;


    }
    public void sendMail(SimpleMailMessage message){
        ArrayList<String> toUserList=new ArrayList<>();
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
