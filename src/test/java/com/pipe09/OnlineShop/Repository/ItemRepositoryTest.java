package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Item.V1.ItemFactory;
import com.pipe09.OnlineShop.Domain.Item.V1.Item_status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback( value = true )
public class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;
    Long testnum = null;
    // 테스트 시작전 호출
    //@BeforeClass 는 static이어서 레포지토리에 접근 할 수 없다.

    @Transactional
    @Before
    public void forUnit(){
        Item item = new ItemFactory().makingItemBytype("누수탐지기");
        item.setName("testman");
        item.setStockQuantity( 10 );
        item.setStatus(Item_status.SALE);
        System.out.println(" ItemRepository Test: TestCase: [ item( LeakDetector ){ name: testman stock: 10 } ]");
        this.testnum = repository.save( item );
        System.out.println( " 저장된 엔티티 아이디 값: " +  testnum );


    }
    @After
    public void setDown(){
        repository.findItem( testnum );
        repository.removeById( testnum );

    }


    @Test
    @DisplayName("저장과 불러오기")
    public void SaveAndLoad(){


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
    @DisplayName("엔티티 변경후 아이템 불러오기")
    public void LoadAfterChange(){
        List<Item>items=repository.findAll(0,200);
        items.stream().forEach(item -> System.out.println(item.getName()));
        // 지금 데베엔 아이템이 있으므로 Null만 아니면 됨.
        assertNotNull( items );
    }
    @Test
    @DisplayName("서칭 테스트")
    public void SearchTest(){
        /*
        Long subnum;
        Item item = new ItemFactory().makingItemBytype("누수탐지기");
        item.setName("ttman");
        item.setStockQuantity( 10 );
        item.setStatus(Item_status.SALE);
        System.out.println(" ItemRepository Test: TestCase: [ item( LeakDetector ){ name: ttman stock: 10 } ]");
        subnum = repository.save( item );
        System.out.println( " 저장된 엔티티 아이디 값: " +  subnum );
        System.out.println("시작 전 저장하였던 Item 검색시 ttman은 하나만 있어야 한다.");
        // keyword 기반 쿼리.


         */


        //TODO 이것이 왜 해결책일까? 이유는 FLUSH다 조회를 하나 flush를 직접 부르나 db와 동기화는 되기 때문에.
        /*
        repository.flush();
        List<Item> tests = repository.findAll(0,1000);
        tests.stream().forEach( test -> { System.out.println( test.getName());});


         */


        Item item2 = repository.findItem( this.testnum );

        item2.setDescription(" hi " );

        System.out.println( "hi: " + item2.getDescription());

        List<Item>items=repository.findBytitleKeyword("testm",0,1000);


        assertEquals(1,items.size());
        System.out.println("두 엔티티는 같은 값을 가져야 한다.");
        assertEquals( item2.getName(), items.get(0).getName());
        assertEquals( item2.getItem_ID(),items.get(0).getItem_ID());
    }


}