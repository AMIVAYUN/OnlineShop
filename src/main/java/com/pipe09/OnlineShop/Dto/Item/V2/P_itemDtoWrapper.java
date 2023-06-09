package com.pipe09.OnlineShop.Dto.Item.V2;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class P_itemDtoWrapper {
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stockquantity;
    @NotNull
    private Integer weight;

    @NotNull
    private String description;
    ////

    @NotNull
    private String madein;

    @NotNull
    private String manufacturedcompany;
    @NotNull
    private String dtype;

    public P_itemDtoV2 getItemDtov2(){
        return P_itemDtoV2.builder().name( this.getName() ).Description( this.getDescription() )
                .price( this.getPrice() ).StockQuantity( this.getStockquantity() )
                .weight( this.getWeight() ).MadeIn( this.getMadein() )
                .ManufacturedCompany( this.getManufacturedcompany() ).dtype_name( this.getDtype() ).build();
    }
}
