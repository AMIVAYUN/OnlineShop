package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Board.Notice;
import groovy.util.logging.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.swing.text.html.parser.Entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
@Rollback( value = true )
public class BoardRepositoryTest {
    /*
    public Long save( Notice notice )
    public List<Notice> findAll()
    public Notice findByID( Long id )
    public boolean removeNoticeByID( Long id )
    public List<Notice> findWithKeyWord( Keyword )
    public List<Notice> findAllWithofflim( Keyword, off, lim )
    public List<Notice> findWithKeyWordWithofflim ( Keyword, off, lim )
     */
    @Autowired
    BoardRepository boardRepository;
    Long saveId;



    @Before
    public void forUnit(){
        Notice notice=Notice.createNotice("testCase","testCase",LocalDate.of(2022,01,29));
        System.out.println("테스트 케이스: \n"+"name:" + notice.getName()+ "desc: " + notice.getDescription() + "date:" + notice.getDate().toString());
        this.saveId= boardRepository.save( notice );
        System.out.println("id: "+ this.saveId.toString());

    }


    @Test
    @DisplayName( "findById Test" )
    public void findAllTest(){
        Notice findNotice = boardRepository.findByID( this.saveId );
        // 여기서 찾은 공지는 아까 넣은 공지와 내용이 같아야 함.
        assertEquals( "testCase",findNotice.getName());
        assertEquals("testCase",findNotice.getDescription());
        assertEquals(LocalDate.of(2022,01,29).toString(),findNotice.getDate().toString());


    }
    @Test
    @DisplayName( "removeById Test" )
    public void removeByIdTest(){
        //ID는 PK이므로 오직 하나뿐이다. 즉 이를 통해 Test Case를 지울 경우 TestCase는 Null이어야 한다.

        boardRepository.removeNoticeByID( this.saveId );
        assertEquals( null, boardRepository.findByID( this.saveId ) );

    }

    @Test
    @DisplayName( "findByKeyword Test" )
    public void findByKeywordTest(){
        //test란 키워드가 들어가는 Entity는 하나여야 하며, 내용이 같아야 한다.
        List< Notice > lst = boardRepository.findWithKeyWord( "test" );
        assertEquals(1,lst.toArray().length);
        assertEquals("testCase",lst.get(0).getName());

    }
    @Test
    @DisplayName( "Keyword with off,lim Test " )
    public void findKeywordWithofflimTest(){
        Notice notice=Notice.createNotice("testCase","testCase",LocalDate.of(2022,01,29));
        System.out.println("테스트 케이스: \n"+"name:" + notice.getName()+ "desc: " + notice.getDescription() + "date:" + notice.getDate().toString());
        Long newId= boardRepository.save( notice );
        System.out.println("원래 있던 id: "+ this.saveId.toString() + " 추가한 id: "+newId );
        //같은 엔티티가 두개 들어갔지만 새로운 엔티티 아이디 -1 만큼 찾았을때는 무조건 하나만 있어야 한다.

        List< Notice > lst = boardRepository.findWithKeyWordWithofflim( "test",0,1 );
        lst.stream().forEach( nt -> System.out.println( nt.getName() + nt.getNotice_ID() ) );
        assertEquals( 1, lst.toArray().length );
        assertEquals( true, notice.getName() == lst.get(0).getName() );




    }
    @Test
    @DisplayName( "find All with offlim ")
    public void findAllwithKeywordTest(){
        // 해당 메소드를 실행시키면 찾은 데이터의 길이는 limit보다 작거나 같아야 한다.


        List< Notice > lst = boardRepository.findAllWithofflim( 0, 30 );
        assertEquals( true, lst.toArray().length <= 30 );

    }






}