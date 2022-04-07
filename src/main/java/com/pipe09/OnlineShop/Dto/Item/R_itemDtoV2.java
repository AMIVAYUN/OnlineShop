package com.pipe09.OnlineShop.Dto.Item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
@Data
public class R_itemDtoV2 {
    private String name;
    private Integer price;
    @JsonProperty("StockQuantity")
    private Integer StockQuantity;
    private Integer weight;
    @JsonProperty("Description")
    private String Description;
    ////
    @JsonProperty("MadeIn")
    private String MadeIn;
    @JsonProperty("ManufacturedCompany")
    private String ManufacturedCompany;
    private String dtype;
    @Nullable
    private String imgSrc;
}
