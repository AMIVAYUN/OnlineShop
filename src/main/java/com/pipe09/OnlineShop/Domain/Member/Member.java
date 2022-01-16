package com.pipe09.OnlineShop.Domain.Member;

import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.util.List;

@Entity
@Getter
@Table(name="MEMBER")
public class Member {
    @Id
    @Column(name = "member_id",nullable = false)
    @Setter
    private String Member_ID;
    @Column(name="pwd",nullable = false)
    private String pwd;
    // 이름은  10자를 넘기면 안되고 비면 안된다.
    @Column(name= "NAME",nullable = false,length=10)
    @Setter
    private String Name;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Orders> ordersList;
    @Enumerated(EnumType.STRING)@Setter
    private RoleType roleType = RoleType.USER;
    @Setter @Getter
    private String Phone_Num;


    public static Member createMember(String id,String name,String Phone_Num){
            Member newMember= new Member();
            newMember.setMember_ID(id);
            newMember.setName(name);
            newMember.setPhone_Num(Phone_Num);
            return newMember;
    }
}
