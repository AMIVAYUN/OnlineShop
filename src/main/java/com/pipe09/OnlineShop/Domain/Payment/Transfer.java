package com.pipe09.OnlineShop.Domain.Payment;


import com.pipe09.OnlineShop.Dto.Payment.TransferDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@DynamicInsert
public class Transfer {
    public String transfer_bank;
    public String Transfer_settlementStatus;
    public static Transfer DtoToTransfer(TransferDto dto){
        if(dto==null){
            return null;
        }
        Transfer transfer=new Transfer();
        transfer.setTransfer_bank(dto.getBank());
        transfer.setTransfer_settlementStatus(dto.getSettlementStatus());
        return transfer;
    }
}
