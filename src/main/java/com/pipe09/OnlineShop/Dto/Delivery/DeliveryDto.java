package com.pipe09.OnlineShop.Dto.Delivery;

import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Dto.DeliOrdersDto;
import lombok.Data;

@Data
public class DeliveryDto {
    private Long delivery_id;
    private DeliOrdersDto order;
    private Deliverystatus status;
    private String address;

    public DeliveryDto(Delivery delivery){
        this.delivery_id=delivery.getDelivery_ID();
        this.order=new DeliOrdersDto(delivery.getOrder());
        this.status=delivery.getStatus();
        this.address=delivery.getDelivery_Address();
    }
}
