package com.pipe09.OnlineShop.Dto.Payment;


import lombok.Data;

@Data
public class approvePaymentDto {
    public String mId;
    public String version;
    public String paymentKey;
    public String orderId;
    public String orderName;
    public String currency;
    public String method;
    public String status;
    public String requestedAt;
    public String approvedAt;
    public boolean useEscrow;
    public boolean cultureExpense;
    public cardDto card;
    public String virtualAccount;
    public String transfer;
    public String mobilePhone;
    public String giftCertificate;
    public String foreignEasyPay;
    public String cashReceipt;
    public String discount;
    public String cancels;
    public String secret;
    public String type;
    public String easyPay;
    public String country;
    public String failure;
    public int totalAmount;
    public int balanceAmount;
    public int suppliedAmount;
    public int vat;
    public int taxFreeAmount;
}