package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Configuration.SwaggerConfig;
import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Item.ItemFactory;
import com.pipe09.OnlineShop.Domain.Item.Typed.LeakDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

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
    @Test
    public void 스텟변경후아이템불러오기(){
        List<Item>items=repository.findAll(0,200);
        items.stream().forEach(item -> System.out.println(item.getName()));
    }
    @Test
    public void 검색테스트(){
        List<Item>items=repository.findBytitleKeyword("테스트",0,100);
        assertEquals(items.size(),2);
    }

}