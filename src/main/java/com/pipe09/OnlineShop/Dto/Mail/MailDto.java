package com.pipe09.OnlineShop.Dto.Mail;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MailDto {
    private String name;
    private String phonenum;
    private String title;
    private String content;
    private Date writtendate;

}
