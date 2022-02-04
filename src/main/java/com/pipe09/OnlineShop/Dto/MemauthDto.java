package com.pipe09.OnlineShop.Dto;


import lombok.Data;

@Data
public class MemauthDto {
    boolean isauth;
    String iswho;
    String iswhom;

    public MemauthDto(boolean isauth, String iswho, String iswhom) {
        this.isauth = isauth;
        this.iswho = iswho;
        this.iswhom = iswhom;
    }
}
