package com.pipe09.OnlineShop.Domain.Payment;


import com.pipe09.OnlineShop.Dto.Payment.DiscountDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

@Getter
@Setter
@Embeddable
@DynamicInsert
public class Discount {
    public Integer discount_amount; //카드사 즉시 할인 프로모션 적용 금액
    public static Discount DtoToDiscount(DiscountDto dto){
        if(dto==null){
            return null;
        }
        Discount newdis=new Discount();
        newdis.setDiscount_amount(dto.getAmount());
        return newdis;
    }
    @PrePersist
    public void prePersist(){
        this.discount_amount=this.discount_amount==null?0:this.discount_amount;
    }
}
