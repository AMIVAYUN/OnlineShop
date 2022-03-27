package com.pipe09.OnlineShop.Dto.Order;


import com.pipe09.OnlineShop.Dto.OrderItem.OrderItemDto;
import com.pipe09.OnlineShop.Dto.OrderItem.createOrderItemDto;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {
    private String name;
    private String phone_num;
    private String email;
    private String totalprice;
    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private List<createOrderItemDto> item;

}
