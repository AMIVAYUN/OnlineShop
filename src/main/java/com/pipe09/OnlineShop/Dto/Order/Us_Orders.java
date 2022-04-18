package com.pipe09.OnlineShop.Dto.Order;


import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
public class Us_Orders {
    private Long order_Id;
    private String order_Title;
    private Deliverystatus status;
    private String imgSrc;
    private Integer totalPrice;
    private Date orderDate;
    @Nullable
    private String transport_doc;
    private String paymentKey;

    public Us_Orders( Orders orders ){
        this.imgSrc=orders.getOrderItems().get(0).getItem().getImgSrc();
        this.totalPrice=orders.getTotalPrice();
        this.order_Id=orders.getOrder_ID();
        this.order_Title=(orders.getOrderItems().get(0).getItem().getName())+" 및 "+orders.getOrderItems().size()+"건";
        this.status=orders.getDeliverystatus();
        this.orderDate=orders.getOrderdate();
        this.transport_doc=orders.getTransport_docnum();
        try{
            this.paymentKey=orders.getPaymentKey();
        }catch(NullPointerException e){
            System.out.println(e);
        }

    }
}
