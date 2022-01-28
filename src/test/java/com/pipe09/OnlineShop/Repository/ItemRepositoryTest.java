package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Item.ItemFactory;
import com.pipe09.OnlineShop.Domain.Item.Typed.LeakDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;
    @Test
    public void 데이터_저장과불러오기(){
        Item item = new ItemFactory().makingItemBytype("누수탐지기");
        item.setName("대봉 누수 탐지기");
        item.setPrice(1000000);
        item.setStockQuantity(10);
        item.setMadeIn("Korea");
        item.setManufacturedCompany("대봉산업기계");
        item.setWeight(5);
        item.setImgSrc("img/LeakDetector.jpg");
        item.setDescription("정말 좋아요");
        Long id=repository.save(item);
        assertEquals(id,item.getItem_ID());
        assertEquals(repository.findItem(id).getDescription(),"정말 좋아요");
        System.out.println(repository.findItem(id).getClass());
    }

}