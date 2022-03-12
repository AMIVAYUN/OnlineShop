package com.pipe09.OnlineShop.Dto.Item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class P_itemDto {
    private String name;
    private Integer price;
    @JsonProperty("stockquantity")
    private Integer stockquantity;
    private Integer weight;
    private String description;
    ////
    @JsonProperty("madein")
    private String madein;
    @JsonProperty("manufacturedcompany")
    private String manufacturedcompany;
    private String dtype;
}
