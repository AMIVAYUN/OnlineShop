package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.Typed.LeakDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;

    @Test
    @Transactional
    public void 데이터_저장과불러오기(){
        LeakDetector item = new LeakDetector();
        item.setName("누수탐지기");
        item.setDescription("정말 좋아요");
        Long id=repository.save(item);
        assertEquals(id,item.getItem_ID());
        assertEquals(repository.findItem(id).getDescription(),"정말 좋아요");
        System.out.println(repository.findItem(id).getClass());
    }

}