package com.pipe09.OnlineShop.Dto.Board;

import lombok.Data;

@Data
public class UpdateNoticeDto {
    private Long notice_id;
    private String name;
    private String description;
}
