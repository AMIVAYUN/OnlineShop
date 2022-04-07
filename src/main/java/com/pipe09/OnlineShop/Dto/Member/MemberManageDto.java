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
        this.phone_num=this.getTelNumber(member.getPhone_Num());
        this.enrollddate=member.getDate();
    }
    public String getTelNumber(String tel) { // 숫자만 남기기
        if(tel==null){
            return null;
        }
        if (tel.length() > 9) {
            String regEx = "(\\d{3})(\\d{4})(\\d{4})";
            if (tel.length() == 9) {
                regEx = "(\\d{2})(\\d{3})(\\d{4})";
            } else if (tel.length() == 10) {
                // 10자리 일 경우 처리가 달라진다. ex) 02-2222-3333 , 011-222-3333
                String chkTel = tel.substring(0, 2); // 앞자리가 02로 시작되면 앞자리가 2자리
                if (chkTel.equals("02")) {
                    regEx = "(\\d{2})(\\d{4})(\\d{4})";
                } else {
                    regEx = "(\\d{3})(\\d{3})(\\d{4})";
                }
            }
            tel = tel.replaceAll(regEx, "$1-$2-$3");
        } else { // 9자리 이하면 그냥 -- 만 추가해준다.
            tel = tel + "--";
        }
        return tel;
    }

}
