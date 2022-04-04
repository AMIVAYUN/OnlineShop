package com.pipe09.OnlineShop.Domain.Payment;

import com.pipe09.OnlineShop.Dto.Payment.FailureDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@DynamicInsert
public class Failure {
    public String code;
    public String message;

    public static Failure DtoToFailure(FailureDto dto){
        if(dto==null){
            return null;
        }
        Failure newfail=new Failure();
        newfail.setCode(dto.getCode());
        newfail.setMessage(dto.getMessage());
        return newfail;
    }
}
