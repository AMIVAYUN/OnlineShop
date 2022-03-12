package com.pipe09.OnlineShop.Dto.Mail;

import lombok.Data;

import java.util.Date;

@Data
public class MailDto {

    private String from;
    private String phone_num;
    private String title;
    private String content;
    private Date writtendate;

}
