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

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="ITEM")
@Slf4j
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
    @Enumerated(EnumType.STRING)
    private Item_status status;

    @Basic(fetch = FetchType.LAZY)
    @Formula("(select count(1) from item as i where i.status='SALE')")
    private int countofItems;



    public static Item createNewItem(String type,String Name,int Price,int StockQuantity,String description,int Weight,String Madein,String ManufacturedCompany){
        ItemFactory factory=new ItemFactory();
        Item newitem=factory.makingItemBytype(type);
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

        int rest=this.getStockQuantity()-count;
        log.info("재고:"+this.getStockQuantity()+", 수량: "+count);

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
    public static Item fromReg(R_itemDto r_itemDto){
        //Item item=createNewItem(r_itemDto.getDtype(),r_itemDto.getName(),r_itemDto.getPrice(),r_itemDto.getStockQuantity(),r_itemDto.getDescription(),r_itemDto.getWeight(),r_itemDto.getMadeIn(),r_itemDto.getManufacturedCompany());
        //item.setImgSrc("img/upload/"+Utils.deleteKorean(r_itemDto.img.getOriginalFilename()));
        r_itemDto.setImgSrc("img/upload/"+Utils.deleteKorean(r_itemDto.img.getOriginalFilename()));
        DefaultMapper<Item> mapper=new DefaultMapper<>(ItemFactory.makingItemBytype(r_itemDto.getDtype()));
        Item item=mapper.Translate(r_itemDto);

        return item;
    }

    /*
    public static Item fromRegv2(R_itemDtoV2 v2){
        System.out.println(v2.getDtype());
        DefaultMapper<Item>mapper=new DefaultMapper<>(ItemFactory.makingItemBytype(v2.getDtype()));
        Item item=mapper.Translate(v2);
        return item;
    }

     */




}

