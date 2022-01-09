package com.pipe09.OnlineShop.Domain;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter

@Table(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long Member_ID;
    // 이름은  10자를 넘기면 안되고 비면 안된다.
    @Column(name= "NAME",nullable = false,length=10)
    @Setter
    private String Name;
    @Setter
    private Long Phone_Num;

    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList;

    @Enumerated
    @Setter
    private RoleType roleType;
}
