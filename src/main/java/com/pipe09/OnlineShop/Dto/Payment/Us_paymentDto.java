package com.pipe09.OnlineShop.Dto.Payment;

import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Payment.payment;
import com.pipe09.OnlineShop.Domain.Payment.paymentType;
import com.pipe09.OnlineShop.Dto.OrderItem.OrderItemDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Us_paymentDto {
    private String paymentKey;
    private String orderId;
    private List<OrderItemDto> itemDtoList;
    private String approvedAt;
    private String method;
    private int totalAmount;
    private paymentType type;

    public Us_paymentDto(payment payment, List<OrderItem> itemList){
        this.orderId=payment.getOrderId();
        this.approvedAt=payment.getApprovedAt();
        this.method=payment.getMethod();
        this.totalAmount=payment.getTotalAmount();
        this.itemDtoList=itemList.stream().map(OrderItemDto::new).collect(Collectors.toList());
        this.paymentKey=payment.getPaymentKey();
        this.type=payment.getCurrent_type();
    }
}
