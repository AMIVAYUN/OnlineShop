package com.pipe09.OnlineShop.Domain.Orders;


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

    @Column
    private Deliverystatus deliverystatus;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date Orderdate;

    public void addOrderItem(OrderItem[] orderItems){
        Arrays.stream(orderItems).forEach(orderItem -> this.orderItems.add(orderItem));
    }

    public static Orders createOrder(Member member,Date orderdate,OrderItem... orderItems){
        Orders order=new Orders();
        order.setMember(member);
        order.setOrderdate(orderdate);
        order.addOrderItem(orderItems);
        order.setDeliverystatus(Deliverystatus.BEFOREPAYMENT);
        return order;
    }

    public void cancel() {
        if(this.getDeliverystatus()== Deliverystatus.COMPLETE){
            throw new IllegalStateException("배송 완료 상품은 불가능 합니다.");
        }
        this.setDeliverystatus(Deliverystatus.CANCEL);
        for(OrderItem item:orderItems){
            
        }
    }
}
