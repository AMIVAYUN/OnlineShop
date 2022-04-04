package com.pipe09.OnlineShop.Domain.Payment;


import com.pipe09.OnlineShop.Dto.Payment.CashReceiptDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.Nullable;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

@Getter
@Setter
@Embeddable
@DynamicInsert
public class CashReceipt {
    public String cashreceipt_type; // 현금 영수증 종류
    public Integer cashreceipt_amount; // 처리 금액
    public Integer cashreceipt_taxFreeAmount;  // 면세 처리 금액
    public String issueNumber; // 현금영수증 발급번호
    public String receiptUrl; //영수증 주소

    public static CashReceipt DtoToCashReceipt(CashReceiptDto dto){
        if(dto==null){
            return null;
        }
        CashReceipt newreceipt=new CashReceipt();
        newreceipt.setReceiptUrl(dto.getReceiptUrl());
        newreceipt.setCashreceipt_type(dto.getType());
        newreceipt.setCashreceipt_amount(dto.getAmount());
        newreceipt.setIssueNumber(dto.getIssueNumber());
        newreceipt.setCashreceipt_taxFreeAmount(dto.getTaxFreeAmount());
        return newreceipt;
    }
    @PrePersist
    public void prePersist(){
        this.cashreceipt_amount=this.cashreceipt_amount==null ? 0:this.cashreceipt_amount;
        this.cashreceipt_taxFreeAmount=this.cashreceipt_taxFreeAmount==null?0:this.cashreceipt_taxFreeAmount;
    }
}
