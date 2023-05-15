package com.pipe09.OnlineShop.Dto.Item.V1;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Dv2_itemDto {
    @NotNull
    public List<Long> itemIdList;
}
