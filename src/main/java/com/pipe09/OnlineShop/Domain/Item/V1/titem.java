package com.pipe09.OnlineShop.Domain.Item.V1;



import com.pipe09.OnlineShop.Dto.Item.V1.R_itemDto;
import com.pipe09.OnlineShop.Dto.Item.V2.itemDtoV2;
import com.pipe09.OnlineShop.Exception.StockLackException;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Utils.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Slf4j
@DiscriminatorColumn(name = "DTYPE")
public class titem {

    @Id
    @GeneratedValue
    private Long Item_ID;


    private String Name;
    private int Price;
    private int StockQuantity;
    @Lob
    private String Description;
    ////
    private int Weight;
    private String MadeIn;
    private String ManufacturedCompany;
    @Enumerated(EnumType.STRING)
    private Item_status status;

    @Version
    private int version;







}

