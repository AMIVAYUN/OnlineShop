package com.pipe09.OnlineShop.Dto.Item.V2;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class P_itemDtoV2{
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
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
    private String dtype_name;


    @Nullable
    private String imgSrc;

    @Override
    public String toString(){
        return this.name + "//" + this.price + "//" + this.dtype_name;
    }
}
