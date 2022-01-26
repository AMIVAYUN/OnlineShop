package com.pipe09.OnlineShop.Domain.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="MEMBER")
public class Member {

    @Column(name = "member_id",nullable = false)
    @Setter
    @Id@GeneratedValue
    private Long Member_ID;
    @Getter@Setter
    private String User_ID;
    @Column(name="pwd",nullable = false)@Setter
    private String pwd;
    // 이름은  10자를 넘기면 안되고 비면 안된다.
    @Column(name= "NAME",nullable = false,length=10)
    @Setter
    private String Name;

    @JsonIgnore
    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Orders> ordersList;
    @Enumerated(EnumType.STRING)@Setter
    private RoleType roleType = RoleType.USER;
    @Setter @Getter
    private String Phone_Num;

    @Column(name = "RegDate")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;


    public static Member createMember(String id,String name,String Phone_Num,String pwd){
            Member newMember= new Member();
            newMember.setUser_ID(id);
            newMember.setName(name);
            newMember.setPhone_Num(Phone_Num);
            newMember.setPwd(pwd);
            newMember.setRoleType(RoleType.USER);
            return newMember;
    }
}
