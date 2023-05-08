package com.pipe09.OnlineShop.Dto.dType;

import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType_Status;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class dTypeDto {

    private Long dtype_id;

    private String name;

    private dType_Status dTypeStatus;

}
