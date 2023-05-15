package com.pipe09.OnlineShop.Domain.Item.V2.DTYPE;


import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Item.V1.Item_status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Dto.Item.V2.itemDtoV2;
import com.pipe09.OnlineShop.Exception.StockLackException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="ITEMv2")
@RequiredArgsConstructor
//@DiscriminatorColumn(name = "DTYPE")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Itemv2 {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long Item_ID;


    private String name;
    private int price;
    private Integer stockQuantity; // 중요
    @Lob
    private String description;
    ////
    private int weight;
    private String madeIn;
    private String manufacturedCompany;
    public String imgSrc;
    @ManyToOne
    @JoinColumn( name = "dtype_id", nullable = false )
    private dType dType;

    @Enumerated(EnumType.STRING)
    private Item_status status;

    @Basic(fetch = FetchType.LAZY)
    @Formula("(select count(1) from item as i where i.status='SALE')")
    private int countofItems;

    //등록
    public Itemv2(itemDtoV2 dto, dType dtype ){
        this.name = dto.getName();
        this.dType = dtype;
        this.price = dto.getPrice();
        this.stockQuantity = dto.getStockQuantity();
        this.description = dto.getDescription();
        this.weight = dto.getWeight();
        this.madeIn = dto.getMadeIn();
        this.manufacturedCompany = dto.getManufacturedCompany();
        this.imgSrc = dto.getImgSrc();
        this.status = Item_status.SALE;
    }

    public Itemv2( String name, Integer price, Integer stockQuantity, String desc, Integer weight, String madeIn, String manufacturedCompany, String imgSrc, dType dType){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = desc;
        this.weight = weight;
        this.madeIn = madeIn;
        this.manufacturedCompany = manufacturedCompany;
        this.imgSrc = imgSrc;
        this.dType = dType;
        this.status = Item_status.SALE;
    }



    public void removeStockQuantity(int count){
        if( this.getStockQuantity() < count) {
            throw new StockLackException();
        }
        else{
            this.stockQuantity-= count;
        }

    }

    public void addStockQuantity(int count){
        this.stockQuantity+=count;
    }

    // For Migration
    public Itemv2( Item item ) {
        this.name = item.getName();
        this.status = item.getStatus();
        this.stockQuantity = item.getStockQuantity();

        //this.dType = new dType( Utils.translateDtype( item.getDTYPE() ) );
        this.madeIn = item.getMadeIn();
        this.imgSrc = item.getImgSrc();
        this.weight = item.getWeight();
        this.description = item.getDescription();
        this.manufacturedCompany = item.getManufacturedCompany();
        this.price = item.getPrice();


    }
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

