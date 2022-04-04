package com.pipe09.OnlineShop.Domain.Payment;

import com.pipe09.OnlineShop.Dto.Payment.VirtualAccountDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

@Getter
@Setter
@Embeddable
@DynamicInsert
public class VirtualAccount {
    public String accountType;
    public String accountNumber;
    public String virtualacc_bank;
    public String customerName;
    public String dueDate;
    public String refundStatus; //NONE: 해당 없음 FAILED: 환불 실패 PENDING: 환불 처리중 PARTIAL_FAILED: 부분환불 실패 COMPLETED: 환불 완료
    public Boolean expired;
    public String virtualaccount_settlementStatus;

    public static VirtualAccount DtotoVritualAccount(VirtualAccountDto dto){
        VirtualAccount newaccount= new VirtualAccount();
        if(dto==null){
            return null;
        }
        newaccount.setAccountNumber(dto.getAccountNumber());
        newaccount.setAccountType(dto.getAccountType());
        newaccount.setVirtualacc_bank(dto.getBank());
        newaccount.setCustomerName(dto.getCustomerName());
        newaccount.setDueDate(dto.getDueDate());
        newaccount.setRefundStatus(dto.getRefundStatus());
        newaccount.setExpired(dto.isExpired());
        newaccount.setVirtualaccount_settlementStatus(dto.getSettlementStatus());
        return newaccount;
    }
    @PrePersist // nullable 상황을 고려한 로직(persist 전에 한번 call)
    public void prePersist(){
        this.expired=this.expired==null?false:this.expired;
    }
}
