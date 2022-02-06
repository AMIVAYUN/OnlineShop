package com.pipe09.OnlineShop;


import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Item.Typed.*;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.nio.file.Watchable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

// 객체를 test용으로 안에 넣을때 사용하는 클래스
@Component
@Transactional
@RequiredArgsConstructor
public class InitSerivce {
    private final EntityManager em;

    public void dbInit(){
        /**
        LeakDetector item = new LeakDetector();
        item.setName("I-970");
        item.setStockQuantity(10);
        item.setManufacturedCompany("Daebong");
        item.setDescription("test");
        item.setImgSrc("img/i-970.jpg");
        item.setWeight(2);
        item.setMadeIn("Korea");
        item.setPrice(1000000);
        em.persist(item);

        SewerCleaner item1= new SewerCleaner();
        item1.setName("DB-150");
        item1.setStockQuantity(10);
        item1.setManufacturedCompany("Daebong");
        item1.setDescription("test");
        item1.setImgSrc("img/DB-150.jpg");
        item1.setWeight(25);
        item1.setMadeIn("Korea");
        item1.setPrice(1000000);
        em.persist(item1);

        SewerCleaner item2= new SewerCleaner();
        item2.setName("D-800B");
        item2.setStockQuantity(10);
        item2.setManufacturedCompany("Daebong");
        item2.setDescription("test");
        item2.setImgSrc("img/D-800B.jpg");
        item2.setWeight(37);
        item2.setMadeIn("Korea");
        item2.setPrice(1000000);
        em.persist(item2);

        Endoscope item3 = new Endoscope();
        item3.setName("CT-50");
        item3.setStockQuantity(10);
        item3.setManufacturedCompany("Daebong");
        item3.setDescription("test");
        item3.setImgSrc("img/CT50.jpg");
        item3.setWeight(2);
        item3.setMadeIn("Korea");
        item3.setPrice(1000000);
        em.persist(item3);

        Cutter item4 = new Cutter();
        item4.setName("63TC");
        item4.setStockQuantity(10);
        item4.setManufacturedCompany("Rothenberger");
        item4.setDescription("플라스틱 가위");
        item4.setImgSrc("img/63TC.jpg");
        item4.setWeight(2);
        item4.setMadeIn("Korea");
        item4.setPrice(5000);
        em.persist(item4);
        Wrench item5 = new Wrench();
        item5.setName("파이프 렌치 36");
        item5.setStockQuantity(10);
        item5.setManufacturedCompany("Rothenberger");
        item5.setDescription("파이프 렌치");
        item5.setImgSrc("img/63TC.jpg");
        item5.setWeight(2);
        item5.setMadeIn("Germany");
        item5.setPrice(8000);
        em.persist(item4);

         */



    }
    public void dbInit2(){
        /*
        Item newitem=em.find(Item.class,30L);
        Member member=em.find(Member.class,5L);

        OrderItem newOrderitem=new OrderItem();
        newOrderitem.setItem(newitem);
        newOrderitem.setCount(1);
        newOrderitem.setPrice(newitem.getPrice());


        Delivery newDeli= new Delivery();
        newDeli.setStatus(Deliverystatus.READY);
        newDeli.setDelivery_Address("안양");


        Orders newOrder=Orders.createOrder(member,newDeli, new Date(),newOrderitem);
        newOrderitem.setOrders(newOrder);
        em.persist(newOrder);

         */







    }
}


