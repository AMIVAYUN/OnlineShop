package com.pipe09.OnlineShop.Domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @ManyToOne
    @JoinColumn(name = "Member_ID")
    private Member member;
    @OneToMany( mappedBy = "order")
    private List<OrderItem> orderItems= new ArrayList<>();

    @OneToOne
    @JoinColumn(name= "Delivery_ID")
    private Delivery delivery;

    @Temporal(TemporalType.TIMESTAMP)
    private Date Orderdate;


}
