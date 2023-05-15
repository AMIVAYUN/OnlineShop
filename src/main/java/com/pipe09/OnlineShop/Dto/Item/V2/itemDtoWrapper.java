package com.pipe09.OnlineShop.Dto.Item.V2;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class itemDtoWrapper {
    @NotNull
    private String name;
    @NotNull
    private Integer price;

    private Integer StockQuantity;
    @NotNull
    private Integer weight;

    @NotNull
    private String Description;
    ////

    @NotNull
    private String MadeIn;

    @NotNull
    private String ManufacturedCompany;
    @NotNull
    private Long dtype_id;
    @NotNull
    private MultipartFile file;

    public itemDtoV2 getItemDtov2(){
        return itemDtoV2.builder().name( this.getName() ).Description( this.getDescription() )
                .price( this.getPrice() ).StockQuantity( this.getStockQuantity() )
                .weight( this.getWeight() ).MadeIn( this.getMadeIn() )
                .ManufacturedCompany( this.getManufacturedCompany() ).dtype_id( this.getDtype_id() ).build();
    }

}
