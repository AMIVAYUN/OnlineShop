package com.pipe09.OnlineShop.Domain.Member;

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
}
