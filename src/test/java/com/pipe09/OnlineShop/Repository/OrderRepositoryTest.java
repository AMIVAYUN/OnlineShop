package com.pipe09.OnlineShop.Repository;


import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest

public class OrderRepositoryTest {
    @Autowired OrderRepository orderRepository;
    @Autowired MemberRepository memberRepository;

    @Transactional
    @Test
    public void 주문_등록과불러오기및탐색(){
        Orders newOrder=new Orders();
        newOrder.setOrderItems(new ArrayList<>());
        Member mem=new Member();
        mem.setName("주석");
        memberRepository.save(mem);
        newOrder.setMember(mem);
        newOrder.setDeliverystatus(Deliverystatus.BEFORE);
        Long id=orderRepository.save(newOrder);
        assertEquals("아이디가 같아야 합니다",id,newOrder.getOrder_ID());
        assertEquals("이름이 같아야 합니다",orderRepository.findOne(id).getMember().getName(),"주석");
        assertEquals("쿼리 결과가 같아야 합니다",orderRepository.findByDeliveryStatus(Deliverystatus.READY).get(0).getOrder_ID(),newOrder.getOrder_ID());
    }

    @Transactional
    @Test
    public void 쿼리테스트(){
        orderRepository.findAll();
        System.out.println("------------------------------");

        orderRepository.findAllwithToOne(0,100);

        System.out.println("------------------------------");
    }

}