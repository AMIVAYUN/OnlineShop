package com.pipe09.OnlineShop.GlobalMapper;

import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Dto.Item.V1.ItemDto;
import com.pipe09.OnlineShop.Repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultMapperTest {
    @Autowired ItemRepository repository;
    @Test
    public void translate() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        /*
        DefaultMapper<TestDto> mapper=new DefaultMapper(new TestDto());
        Item newitem=repository.findItem(30L);
        TestDto dto= mapper.Translate(newitem);
        System.out.println(dto.toString());

         */
        DefaultMapper<ItemDto> mapper=new DefaultMapper<>(new ItemDto());
        Item newitem=repository.findItem(30L);
        ItemDto dto=mapper.Translate(newitem);
        System.out.println(dto.toString());
    }
}