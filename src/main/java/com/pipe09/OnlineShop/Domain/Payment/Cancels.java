package com.pipe09.OnlineShop.Domain.Payment;

import com.pipe09.OnlineShop.Dto.Payment.CancelsDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ManyToOne()
    @JoinColumn(name = "cancels")
    private payment payment;

    public static void processObjectArrayCancel(List<Map<String,Object>> dto, payment payment1){

        dto.stream().forEach( obj -> {
            Cancels newcancel=new Cancels();
            newcancel.setCancelAmount((Integer) obj.get("cancelAmount"));
            newcancel.setCancelReason((String) obj.get("cancelReason"));
            newcancel.setTaxFreeAmount((Integer) obj.get("taxFreeAmount"));
            newcancel.setTaxAmount((Integer) obj.get("taxAmount"));
            newcancel.setRefundableAmount((Integer) obj.get("refundableAmount"));
            newcancel.setCanceledAt((String) obj.get("canceledAt"));
            newcancel.setPayment(payment1);
            payment1.getCancels().add(newcancel);
        });


    }



}


