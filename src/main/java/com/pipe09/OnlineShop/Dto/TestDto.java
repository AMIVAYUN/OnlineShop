package com.pipe09.OnlineShop.Dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TestDto {
    private Long Item_ID;
    private String Name;
    private int Price;
    private int StockQuantity;
    private String Description;
    private int Weight;
    private String DTYPE;

    @Override
    public String toString(){
        return this.getItem_ID()+this.getPrice()+this.getPrice()+this.getName();
    }
}
