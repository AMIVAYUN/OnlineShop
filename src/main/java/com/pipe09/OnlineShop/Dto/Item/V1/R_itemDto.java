package com.pipe09.OnlineShop.Dto.Item.V1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class R_itemDto {
    private String name;
    private Integer price;
    private Integer StockQuantity;
    private Integer weight;
    private String Description;
    ////
    private String MadeIn;
    private String ManufacturedCompany;
    @NotNull
    public MultipartFile img;
    private String dtype;
    private String imgSrc;
    /*
    public R_itemDto(String name, int price, int stockQuantity, int weight, String description, String madeIn, String manufacturedCompany, MultipartFile img, String dtype) {
        this.name = name;
        this.price = price;
        StockQuantity = stockQuantity;
        this.weight = weight;
        Description = description;
        MadeIn = madeIn;
        ManufacturedCompany = manufacturedCompany;
        this.img = img;
        this.dtype = dtype;
    }

     */
}
