package com.pipe09.OnlineShop.Controller.ApiController;

/*
import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Dto.Delivery.DeliveryDto;
import com.pipe09.OnlineShop.Service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DeliveryApiController {
    private final DeliveryService service;
    @GetMapping("/api/v1/delivery/all")
    public List<DeliveryDto> findall(@RequestParam int offset,@RequestParam int limit){
        List<Delivery> list=service.findAll(offset,limit);
        List<DeliveryDto> dtoList=list.stream().map(DeliveryDto::new).collect(Collectors.toList());
        return dtoList;
    }
}


 */
