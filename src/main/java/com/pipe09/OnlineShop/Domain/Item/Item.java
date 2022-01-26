package com.pipe09.OnlineShop.Domain.Item;


import com.pipe09.OnlineShop.Dto.M_ItemDto;
import com.pipe09.OnlineShop.Dto.R_itemDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name="ITEM")
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Item {

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
    @Column(name="DTYPE",insertable = false,updatable = false)
    private String DTYPE;
    public static Item createNewItem(String Name,int Price,int StockQuantity,String description,int Weight,String Madein,String ManufacturedCompany){
        Item newitem=new Item();
        newitem.setName(Name);
        newitem.setPrice(Price);
        newitem.setStockQuantity(StockQuantity);
        newitem.setDescription(description);
        newitem.setWeight(Weight);
        newitem.setMadeIn(Madein);
        newitem.setManufacturedCompany(ManufacturedCompany);
        return newitem;
    }
    public void removeStockQuantity(int count){
        int rest=this.StockQuantity-count;
        if(rest<0){
            throw new RuntimeException("재고 수량이 부족합니다");
        }
        this.setStockQuantity(rest);
    }
    public void addStockQuantity(int count){
        this.StockQuantity+=count;
    }
    public static List<M_ItemDto> itemtoDto(List<Item> items){
        List<M_ItemDto> dtoList=items.stream().map(M_ItemDto::new).collect(Collectors.toList());
        /** 도메인 정재 */
        dtoList.stream()
                .forEach(m_itemDto -> m_itemDto.setDtype(
                        m_itemDto.getDtype().substring("com.pipe09.OnlineShop.Domain.Item.Typed.".length())));
        return dtoList;
    }
    public static Item fromReg(R_itemDto r_itemDto,String src){
        Item item=createNewItem(r_itemDto.getName(),r_itemDto.getPrice(),r_itemDto.getStockQuantity(),r_itemDto.getDescription(),r_itemDto.getWeight(),r_itemDto.getMadeIn(),r_itemDto.getManufacturedCompany());
        item.setImgSrc("");
        return item;
    }

}
