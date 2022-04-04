package com.pipe09.OnlineShop.Domain.Payment;

import com.pipe09.OnlineShop.Dto.Payment.CancelsDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.HashMap;

@Getter
@Setter
@Entity
@DynamicInsert
public class Cancels {
    @Id
    @GeneratedValue
    private Long payment_cancelId;
    private Integer cancelAmount;
    private String cancelReason;
    private Integer taxFreeAmount;
    @Nullable
    private Integer taxAmount;
    private Integer refundableAmount;
    private String canceledAt;
    @OneToOne(mappedBy = "cancels")
    private payment payment;
    public static Cancels processObjectArrayCancel(HashMap<String,Object> dto, payment payment1){
        if(dto==null){
            return null;
        }
        Cancels newcancel=new Cancels();
        newcancel.setCancelAmount((Integer) dto.get("cancelAmount"));
        newcancel.setCancelReason((String) dto.get("cancelReason"));
        newcancel.setTaxFreeAmount((Integer) dto.get("taxFreeAmount"));
        newcancel.setTaxAmount((Integer) dto.get("taxAmount"));
        newcancel.setRefundableAmount((Integer) dto.get("refundableAmount"));
        newcancel.setCanceledAt((String) dto.get("canceledAt"));
        newcancel.setPayment(payment1);
        return newcancel;
    }

}
