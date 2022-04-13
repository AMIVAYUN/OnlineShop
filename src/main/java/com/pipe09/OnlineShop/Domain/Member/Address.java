package com.pipe09.OnlineShop.Domain.Member;

import com.pipe09.OnlineShop.Dto.Member.AddressDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@Getter
@Setter
public class Address {
    private String postcode;
    @Lob
    private String address;
    @Lob
    private String detailAddress;
    @Lob
    private String ref_address;

    public void IntegeratedSetter(AddressDto dto){
        this.setPostcode(dto.getPostcode());
        this.setAddress(dto.getAddress());
        this.setDetailAddress(dto.getDetailAddress());
        this.setRef_address(dto.getRef_address());
    }
}
