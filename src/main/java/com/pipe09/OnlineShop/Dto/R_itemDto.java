package com.pipe09.OnlineShop.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
@Data
public class R_itemDto {
    private String name;
    private int price;
    private int StockQuantity;
    private int weight;
    private String Description;
    ////

    private String MadeIn;
    private String ManufacturedCompany;
    public MultipartFile img;
    private String dtype;

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
}
