package com.pipe09.OnlineShop.Dto.Board;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NoticeDto {

    private Long notice_id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;

    public NoticeDto(){

    }
}
