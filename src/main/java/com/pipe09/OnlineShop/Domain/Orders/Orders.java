package com.pipe09.OnlineShop.Domain.Orders;


import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "ORDERS")
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Order_ID;
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_ID",nullable = false)
    private Member member;

    @OneToMany( mappedBy = "orders",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderItem> orderItems= new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name= "Delivery_ID")
    private Delivery delivery;

    @Temporal(TemporalType.TIMESTAMP)
    private Date Orderdate;


    public void cancel() {
        if(this.delivery.getStatus()== Deliverystatus.COMPLETE){
            throw new IllegalStateException("배송 완료 상품은 불가능 합니다.");
        }
        this.delivery.setStatus(Deliverystatus.CANCEL);
        for(OrderItem item:orderItems){
            
        }
    }
}
