package com.pipe09.OnlineShop.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {
    @Id
    private Long Member_ID;

    private String Name;
    private Long Phone_Num;
}
