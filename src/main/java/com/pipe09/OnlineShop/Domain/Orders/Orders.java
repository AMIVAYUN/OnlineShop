package com.pipe09.OnlineShop.Domain.Orders;


import com.pipe09.OnlineShop.Domain.Board.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Member.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    @Enumerated(value = EnumType.STRING)
    private Deliverystatus deliverystatus;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date Orderdate;

    @Column(name="totalprice")
    private int totalPrice;

    @Column(name ="paymentKey")
    private String paymentKey;

    public void addOrderItem(OrderItem[] orderItems,Orders order){
        Arrays.stream(orderItems).forEach(orderItem -> {
            orderItem.setOrders(order);
            this.orderItems.add(orderItem);
        });
    }

    public static Orders createOrder(Member member,Date orderdate,OrderItem... orderItems){
        Orders order=new Orders();
        order.setMember(member);
        order.setOrderdate(orderdate);
        order.addOrderItem(orderItems,order);
        order.setDeliverystatus(Deliverystatus.BEFORE);
        return order;
    }

    public static Orders createOrder(Member member,Date orderdate,List<OrderItem> orderItemList,String totalprice){
        Orders order=new Orders();
        order.setMember(member);
        order.setOrderdate(orderdate);
        OrderItem[] items=orderItemList.toArray(OrderItem[]::new);
        order.addOrderItem(items,order);
        order.setDeliverystatus(Deliverystatus.BEFORE);
        //정규 표현식
        int totalPrice=Integer.valueOf(totalprice.replaceAll("[^0-9]", ""));
        order.setTotalPrice(totalPrice);
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
    @Override
    public String toString(){
        return this.getOrder_ID()+"-"+this.getDeliverystatus()+this.getTotalPrice();
    }
}
