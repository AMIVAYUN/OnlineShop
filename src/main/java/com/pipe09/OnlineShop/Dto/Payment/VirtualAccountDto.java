package com.pipe09.OnlineShop.Dto.Payment;


import lombok.Data;

@Data
public class VirtualAccountDto {
    public String accountType;
    public String accountNumber;
    public String bank;
    public String customerName;
    public String dueDate;
    public String refundStatus; //NONE: 해당 없음 FAILED: 환불 실패 PENDING: 환불 처리중 PARTIAL_FAILED: 부분환불 실패 COMPLETED: 환불 완료
    public boolean expired;
    public String settlementStatus;


}
