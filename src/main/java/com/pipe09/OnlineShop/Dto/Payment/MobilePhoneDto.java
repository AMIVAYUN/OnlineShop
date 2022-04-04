package com.pipe09.OnlineShop.Dto.Payment;


import lombok.Data;

@Data
public class MobilePhoneDto {
    public String carrier;
    public String customerMobilePhone;
    public String settlementStatus;
}
