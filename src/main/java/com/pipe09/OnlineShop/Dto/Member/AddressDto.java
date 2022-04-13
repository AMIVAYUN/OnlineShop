package com.pipe09.OnlineShop.Dto.Member;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class AddressDto {
    private String postcode;

    private String address;

    private String detailAddress;

    private String ref_address;
}
