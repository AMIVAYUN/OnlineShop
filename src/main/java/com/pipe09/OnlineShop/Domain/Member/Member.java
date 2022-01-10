package com.pipe09.OnlineShop.Domain.Member;

import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name="MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long Member_ID;
    // 이름은  10자를 넘기면 안되고 비면 안된다.
    @Column(name= "NAME",nullable = false,length=10)
    @Setter
    private String Name;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Orders> ordersList;
    @Enumerated(EnumType.STRING)@Setter
    private RoleType roleType;
    @Setter @Getter
    private String Phone_Num;


    public Member createMember(String name,String Phone_Num){
            Member newMember= new Member();
            newMember.setName(name);
            newMember.setPhone_Num(Phone_Num);
            newMember.setRoleType(RoleType.USER);
            return newMember;
    }
}
