package com.pipe09.OnlineShop.Dto.Payment;


import lombok.Data;

@Data
public class CashReceiptDto {
    public String type; // 현금 영수증 종류
    public int amount; // 처리 금액
    public int taxFreeAmount;  // 면세 처리 금액
    public String issueNumber; // 현금영수증 발급번호
    public String receiptUrl; //영수증 주소
}
