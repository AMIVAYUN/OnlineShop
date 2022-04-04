package com.pipe09.OnlineShop.Domain.Payment;


import com.pipe09.OnlineShop.Dto.Payment.GiftCertificateDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@DynamicInsert
public class GiftCertificate {
    public String giftcertificate_approveNo;
    public String giftcertificate_settlementStatus;
    public static GiftCertificate DtoToGiftCertificate(GiftCertificateDto dto){
        if(dto==null){
            return null;
        }
        GiftCertificate newgift=new GiftCertificate();
        newgift.setGiftcertificate_approveNo(dto.getApproveNo());
        newgift.setGiftcertificate_settlementStatus(dto.getSettlementStatus());
        return newgift;
    }
}
