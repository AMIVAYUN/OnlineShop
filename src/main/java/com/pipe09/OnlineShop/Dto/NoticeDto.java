package com.pipe09.OnlineShop.Dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeDto {
    private String name;
    private String description;

    public NoticeDto(String name, String desc) {
        this.name = name;
        this.description = desc;
    }
}
