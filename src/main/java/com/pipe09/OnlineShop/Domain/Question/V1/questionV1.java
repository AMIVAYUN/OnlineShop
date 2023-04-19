package com.pipe09.OnlineShop.Domain.Question.V1;


import com.pipe09.OnlineShop.Domain.Member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class questionV1 {
    @Id
    @GeneratedValue
    private Long question_Id;

    @ManyToOne
    @JoinColumn( name = "Member_ID", nullable = false )
    private Member member;



    @Lob
    private String contents;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date writtenDate;

}
