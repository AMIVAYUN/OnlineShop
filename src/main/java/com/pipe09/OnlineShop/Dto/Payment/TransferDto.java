package com.pipe09.OnlineShop.Dto.Payment;

import lombok.Data;

@Data
public class TransferDto {
    public String bank;
    public String settlementStatus;
}
