package com.pipe09.OnlineShop.Domain.Board;


import com.pipe09.OnlineShop.Dto.Board.NoticeDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "NOTICE")

public class Notice {

    @Id
    @GeneratedValue
    private Long notice_ID;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;
    private String name;
    @Lob
    private String description;





    public static Notice fromDto(NoticeDto dto){
        return createNotice(dto.getName(), dto.getDescription(),dto.getDate());
    }
    public static Notice createNotice(String name,String desc,LocalDate date){
        Notice notice=new Notice();
        notice.setName(name);
        notice.setDescription(desc);
        notice.setDate(date);
        return notice;
    }
    public void setUploadDate(){
        this.date=LocalDate.now();
    }



}
