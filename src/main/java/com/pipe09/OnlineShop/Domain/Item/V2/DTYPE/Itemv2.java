package com.pipe09.OnlineShop.Domain.Item.V2.DTYPE;


import com.pipe09.OnlineShop.Domain.Item.V1.ItemFactory;
import com.pipe09.OnlineShop.Domain.Item.V1.Item_status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Dto.Item.R_itemDto;
import com.pipe09.OnlineShop.Dto.Item.R_itemDtoV2;
import com.pipe09.OnlineShop.Exception.StockLackException;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Utils.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="ITEM2")
@RequiredArgsConstructor
//@DiscriminatorColumn(name = "DTYPE")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Itemv2 {

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
    public String imgSrc;
    @OneToOne( cascade = CascadeType.ALL )
    private dType DTYPE;
    @Enumerated(EnumType.STRING)
    private Item_status status;

    @Basic(fetch = FetchType.LAZY)
    @Formula("(select count(1) from item as i where i.status='SALE')")
    private int countofItems;


    public Itemv2( String name, Integer price, Integer stockQuantity, String desc, Integer weight, String madeIn, String manufacturedCompany, String imgSrc, dType dType){
        this.Name = name;
        this.Price = price;
        this.StockQuantity = stockQuantity;
        this.Description = desc;
        this.Weight = weight;
        this.MadeIn = madeIn;
        this.ManufacturedCompany = manufacturedCompany;
        this.imgSrc = imgSrc;
        this.DTYPE = dType;
        this.status = Item_status.SALE;
    }

    public void removeStockQuantity(int count){

        int rest=this.getStockQuantity()-count;
        System.out.println("재고:"+this.getStockQuantity()+", 수량: "+count);
        System.out.println(rest);
        if(rest<0){
            throw new StockLackException("재고 수량이 부족합니다");
        }
        this.setStockQuantity(rest);
    }
    public void addStockQuantity(int count){
        this.StockQuantity+=count;
    }
    /** 도메인 정재 */
    /*
    public static List<M_ItemDto> itemtoDto(List<Item> items){
        List<M_ItemDto> dtoList=items.stream().map(M_ItemDto::new).collect(Collectors.toList());

        dtoList.stream()
                .forEach(m_itemDto -> m_itemDto.setDtype(
                        m_itemDto.getDtype().substring("com.pipe09.OnlineShop.Domain.Item.V1.Typed.".length())));
        return dtoList;
    }
    */






}

