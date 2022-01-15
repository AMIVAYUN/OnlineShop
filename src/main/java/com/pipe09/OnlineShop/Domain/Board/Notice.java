package com.pipe09.OnlineShop.Domain.Board;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "NOTICE")
public class Notice {
    @Id
    @GeneratedValue
    private Long Notice_ID;

    private String Name;
    @Lob
    private String Description;



}
