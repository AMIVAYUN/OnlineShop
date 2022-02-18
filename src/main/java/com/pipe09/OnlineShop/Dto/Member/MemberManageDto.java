package com.pipe09.OnlineShop.Dto.Member;

import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.RoleType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberManageDto {
    private Long member_id;
    private RoleType roleType;
    private String username;
    private String email;
    private String name;
    private String phone_num;
    private LocalDate enrollddate;
    public MemberManageDto(Member member){
        this.member_id=member.getMember_ID();
        this.roleType=member.getRoleType();
        this.username=member.getUser_ID();
        this.email=member.getEmail();
        this.name=member.getName();
        this.phone_num=member.getPhone_Num();
        this.enrollddate=member.getDate();
    }
}
