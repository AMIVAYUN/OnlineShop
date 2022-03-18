package com.pipe09.OnlineShop.Domain.Orders;


import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Member.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "ORDERS")
@Entity
public class Orders {
    @Id
    @GeneratedValue
    private Long Order_ID;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_ID",nullable = false)
    private Member member;


    @OneToMany( mappedBy = "orders",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<OrderItem> orderItems= new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name= "Delivery_ID")
    private Delivery delivery;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date Orderdate;

    public void addOrderItem(OrderItem[] orderItems){
        Arrays.stream(orderItems).forEach(orderItem -> this.orderItems.add(orderItem));
    }

    public static Orders createOrder(Member member,Delivery delivery, Date orderdate,OrderItem... orderItems){
        Orders order=new Orders();
        order.setMember(member);
        order.setDelivery(delivery);
        order.getDelivery().setOrder(order);
        order.setOrderdate(orderdate);
        order.addOrderItem(orderItems);
        return order;
    }

    public void cancel() {
        if(this.delivery.getStatus()== Deliverystatus.COMPLETE){
            throw new IllegalStateException("배송 완료 상품은 불가능 합니다.");
        }
        this.delivery.setStatus(Deliverystatus.CANCEL);
        for(OrderItem item:orderItems){
            
        }
    }
}
