package com.pipe09.OnlineShop.Domain.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Setter@Column( nullable = false )
    private String user_ID;
    @Column(name="pwd",nullable = false)@Setter
    private String pwd;
    // 이름은  10자를 넘기면 안되고 비면 안된다.
    @Column(name= "NAME",nullable = false,length=10)
    @Setter
    private String Name;
    @Setter
    @Column(name = "EMAIL",nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Orders> ordersList;
    @Enumerated(EnumType.STRING)@Setter
    private RoleType roleType = RoleType.USER;
    @Column(nullable = false)
    @Setter
    private String Phone_Num;

    @Column(name = "RegDate")@Setter
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Mem_status stat;
    @Lob
    @Nullable
    private String withdraw_reason;
    public static Member createMember(String id,String name,String Phone_Num,String pwd,String email) {
        Member newMember = new Member();
        newMember.setUser_ID(id);
        newMember.setName(name);
        newMember.setPhone_Num(Phone_Num);
        newMember.setPwd(pwd);
        newMember.setEmail(email);
        newMember.setRoleType(RoleType.USER);
        newMember.setDate(LocalDate.now());
        return newMember;
    }
    public void Encodepwd(PasswordEncoder encoder){
        this.pwd=encoder.encode(this.pwd);
    }
}
