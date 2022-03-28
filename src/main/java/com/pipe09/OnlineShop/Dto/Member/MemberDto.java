package com.pipe09.OnlineShop.Dto.Member;

import com.pipe09.OnlineShop.Domain.Member.Address;
import lombok.Data;

@Data
public class MemberDto {
    private String username;
    private String password;
    private String email;
    private String name;
    private String phone_num;
    private Address address;
    public MemberDto(){

    }
    public MemberDto(String username, String password, String email, String name, String phone_num,Address address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone_num = phone_num;
        this.address=address;
    }
}
