package com.pipe09.OnlineShop.Dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class responseDto {
    Exception e;
    String desc;

    public responseDto(HttpStatus status,Exception e, String desc) {
        this.e = e;
        this.desc = desc;
    }
}
