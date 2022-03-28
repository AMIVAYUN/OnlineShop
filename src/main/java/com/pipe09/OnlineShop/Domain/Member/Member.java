package com.pipe09.OnlineShop.Domain.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipe09.OnlineShop.Domain.Orders.Orders;

import com.pipe09.OnlineShop.Domain.Shoplist.ShopCart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime; 
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="MEMBER")
public class Member {

    @Column(name = "member_id",nullable = false)
    @Id@GeneratedValue
    private Long Member_ID;
    @Column( nullable = false )
    private String user_ID;
    @Column(name="pwd",nullable = false)@Setter
    private String pwd;
    // 이름은  10자를 넘기면 안되고 비면 안된다.
    @Column(name= "NAME",nullable = false,length=10)
    private String Name;
    @Column(name = "EMAIL",nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Orders> ordersList;
    @Enumerated(EnumType.STRING)@Setter
    private RoleType roleType = RoleType.USER;
    @Column(nullable = true)
    private String Phone_Num;

    @Column(name = "RegDate")@Setter
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Mem_status stat;
    @Lob
    @Nullable
    private String withdraw_reason;
    @Column(name = "PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType userType;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "shoplist_ID")
    private ShopCart shopCart;
    @Column(name="address")
    @Embedded
    private Address address;
    public static Member createMember(String id,String name,String Phone_Num,String pwd,String email,Address address) {
        Member newMember = new Member();
        newMember.setUser_ID(id);
        newMember.setName(name);
        newMember.setPhone_Num(Phone_Num);
        newMember.setPwd(pwd);
        newMember.setEmail(email);
        newMember.setRoleType(RoleType.USER);
        newMember.setUserType(UserType.LOCAL);
        newMember.setStat(Mem_status.ACTIVATED);
        newMember.setDate(LocalDate.now());
        newMember.setAddress(address);
        newMember.setShopCart(new ShopCart());

        return newMember;
    }
    public void Encodepwd(PasswordEncoder encoder){
        this.pwd=encoder.encode(this.pwd);
    }
    public Member updateName(String name){
        this.setName(name);
        return this;

    }
}
