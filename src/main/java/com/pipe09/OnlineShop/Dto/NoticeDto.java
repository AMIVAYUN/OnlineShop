package com.pipe09.OnlineShop.Dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NoticeDto {

    private Long id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;

    public NoticeDto(){

    }
}
