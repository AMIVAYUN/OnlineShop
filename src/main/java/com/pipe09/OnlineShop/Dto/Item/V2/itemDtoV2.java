package com.pipe09.OnlineShop.Dto.Item.V2;

import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class itemDtoV2 {
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
    private Long dtype_id;


    @Nullable
    private String imgSrc;

    @Override
    public String toString(){
        return this.name + "//" + this.price + "//" + this.dtype_id;
    }




        /*
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.StockQuantity = dto.getStockQuantity();
        this.weight = dto.getWeight();
        this.Description = dto.getDescription();
        this.MadeIn = dto.getMadeIn();
        this.ManufacturedCompany = dto.getManufacturedCompany();
        this.dtype_id = dtype_id;

         */



}
