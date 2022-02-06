package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Dto.OrderDto;
import com.pipe09.OnlineShop.Dto.OrderItemDto;
import com.pipe09.OnlineShop.Repository.OrderRepository;
import com.pipe09.OnlineShop.Service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    @GetMapping("/api/v1/orders/all")
    public List<OrderDto> getOrders(@RequestParam(value = "offset") int offset,@RequestParam int limit){
        List<Orders> orders=orderService.findAllwithToOne(offset,limit);

        List<OrderDto> orderDtos=orders.stream().map(OrderDto::new).collect(Collectors.toList());

        return orderDtos;
    }


}
