package com.pipe09.OnlineShop.Domain;

import com.pipe09.OnlineShop.Domain.Member.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String Type;
}
