package com.pipe09.OnlineShop.Dto.Payment;


import lombok.Data;

@Data
public class requestApproveDto {
    public String orderId;
    public int amount;
}
