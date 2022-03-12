package com.pipe09.OnlineShop.Dto.Member;

import lombok.Data;

@Data
public class MemberDto {
    private String username;
    private String password;
    private String email;
    private String name;
    private String phone_num;

    public MemberDto(String username, String password, String email, String name, String phone_num) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone_num = phone_num;
    }
}
