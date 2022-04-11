package com.pipe09.OnlineShop.Dto.Payment;


import com.pipe09.OnlineShop.Domain.Payment.Cancels;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class approvePaymentDto {
    public String mId;
    public String version;
    public String paymentKey;
    public String orderId;
    public String orderName;
    public String currency;
    public String method;
    public String status; //READY,IN_PROGRESS,WAITING_FOR_DEPOSIT,DONE,CANCELED,PARTIAL_CANCELED,ABORTED,EXPIRED
    public String requestedAt;
    public String approvedAt;
    public boolean useEscrow;
    public boolean cultureExpense;
    public cardDto card;
    public VirtualAccountDto virtualAccount;
    public TransferDto transfer;
    public MobilePhoneDto mobilePhone;
    public GiftCertificateDto giftCertificate;
    public String foreignEasyPay;
    public CashReceiptDto cashReceipt;
    public DiscountDto discount;
    public List<Map<String,Object>> cancels; //cancelAmount(취소금액),cancelReason(취소사유),taxFreeAmount(면세처리금액),taxAmount(과세금액),refundableAmount(취소후 환불가능금액),canceledAt(취소 날짜정보 YYYY-MM-dd'T'HH:mm:SS±hh:mm)
    //순서대로  NUM,STRING,NUM,NUM,STRING
    public String secret; //가상계좌 콜백 시크릿키
    public String type;
    public String easyPay; //간편결제 타입 정보
    public String country; //결제 국가
    public FailureDto failure; //결제 실패 내용
    public int totalAmount;
    public int balanceAmount;
    public int suppliedAmount;
    public int vat;
    public int taxFreeAmount;
    public String code;
    public String message;
}