package com.pipe09.OnlineShop.Dto.Payment;

import lombok.Data;

@Data
public class cardDto {
    public String company;
    public String number;
    public int installmentPlanMonths;
    public boolean isInterestFree;
    public String approveNo;
    public boolean useCardPoint;
    public String cardType;
    public String ownerType;
    public String acquireStatus;
    public String receiptUrl;


}
