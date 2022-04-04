package com.pipe09.OnlineShop.Dto.Payment;

import lombok.Data;

@Data
public class CancelsDto {
    public Long payment_cancelId;
    public int cancelAmount;
    public String cancelReason;
    public int taxFreeAmount;
    public int taxAmount;
    public int refundableAmount;
    public String canceledAt;
}
