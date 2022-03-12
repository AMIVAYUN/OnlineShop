package com.pipe09.OnlineShop.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender sender;

    public void sendMail(){
        ArrayList<String> toUserList=new ArrayList<>();

        toUserList.add("wntjrdbs@gmail.com");
        int size=toUserList.size();
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo((String[])toUserList.toArray(new String[size]));
        message.setFrom("user");
        message.setSubject("test");
        message.setText("테스트입니다");
        sender.send(message);
    }

}
