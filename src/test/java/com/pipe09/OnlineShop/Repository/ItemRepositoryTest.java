package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired ItemRepository repository;

    @Test
    public void 데이터_저장과불러오기(){
        Item item=new Item();
        item.setName("누수탐지기");
        item.setDescription("정말 좋아요");
        Long id=repository.save(item);
        assertEquals(id,item.getItem_ID());
        assertEquals(repository.findItem(id).getDescription(),"정말 좋아요");
    }

}