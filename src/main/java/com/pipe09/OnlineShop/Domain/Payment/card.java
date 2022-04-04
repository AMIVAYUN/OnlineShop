package com.pipe09.OnlineShop.Domain.Payment;


import com.pipe09.OnlineShop.Dto.Payment.cardDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
@Getter
@Setter
@Entity
@DynamicInsert
public class card {
    @Id
    @GeneratedValue
    private Long card_id;
    @OneToOne(mappedBy = "card")
    private payment payment;
    public String company;
    public String number;
    public Integer installmentPlanMonths;
    public Boolean isInterestFree;
    public String approveNo;
    public Boolean useCardPoint;
    public String cardType;
    public String ownerType;
    public String acquireStatus;
    public String receiptUrl;

    public static card CardDtotoCard(cardDto dto,payment payment1){
        if(dto==null){
            return null;
        }
        card newcard= new card();
        newcard.setPayment(payment1);
        newcard.setCompany(dto.getCompany());
        newcard.setNumber(dto.getNumber());
        newcard.setInstallmentPlanMonths(dto.getInstallmentPlanMonths());
        newcard.setIsInterestFree(dto.isInterestFree());
        newcard.setApproveNo(dto.getApproveNo());
        newcard.setUseCardPoint(dto.isUseCardPoint());
        newcard.setCardType(dto.getCardType());
        newcard.setOwnerType(dto.getOwnerType());
        newcard.setAcquireStatus(dto.getAcquireStatus());
        newcard.setReceiptUrl(dto.getReceiptUrl());
        return newcard;
    }
    @PrePersist
    public void prePersist(){
        this.installmentPlanMonths= this.installmentPlanMonths == null ? 0:this.installmentPlanMonths;
        this.isInterestFree= this.isInterestFree==null ? false :this.isInterestFree;
        this.useCardPoint= this.useCardPoint==null ? false : this.useCardPoint;
    }
}
