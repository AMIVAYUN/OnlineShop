package com.pipe09.OnlineShop.Domain.Board;


import com.pipe09.OnlineShop.Dto.NoticeDto;
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
    private Long Notice_ID;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;
    private String Name;
    @Lob
    private String Description;





    public static Notice fromDto(NoticeDto dto){
        return createNotice(dto.getName(), dto.getDescription());
    }
    public static Notice createNotice(String name,String desc){
        Notice notice=new Notice();
        notice.setName(name);
        notice.setDescription(desc);
        notice.setUploadDate();
        return notice;
    }
    public void setUploadDate(){
        this.date=LocalDate.now();
    }


}
