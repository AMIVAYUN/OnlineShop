package com.pipe09.OnlineShop.Dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class responseDto<T> {
    private int code=0;
    String desc;
    private T data=null;

    public responseDto(int code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }
    public static boolean validateNull(Object object){
        if(object==null){
            return false;
        }
        else{
            return true;
        }
    }
}
