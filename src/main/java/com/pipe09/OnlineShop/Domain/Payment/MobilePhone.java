package com.pipe09.OnlineShop.Domain.Payment;


import com.pipe09.OnlineShop.Dto.Payment.MobilePhoneDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Setter
@Embeddable
@DynamicInsert
public class MobilePhone {
    public String carrier;
    public String customerMobilePhone;
    public String mobilephone_settlementStatus;

    public static MobilePhone DtoToMobilePhone(MobilePhoneDto dto){
        if(dto==null){
            return null;
        }
        MobilePhone newPhone=new MobilePhone();
        newPhone.setCarrier(dto.getCarrier());
        newPhone.setCarrier(dto.getCarrier());
        newPhone.setMobilephone_settlementStatus(dto.getSettlementStatus());
        return newPhone;
    }
}
