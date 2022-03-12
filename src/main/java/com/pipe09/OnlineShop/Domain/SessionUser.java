package com.pipe09.OnlineShop.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SessionUser implements Serializable {
    private String name;
    private String email;
}
