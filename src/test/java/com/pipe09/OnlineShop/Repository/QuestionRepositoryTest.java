package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Member.Address;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.UserType;
import com.pipe09.OnlineShop.Domain.Question.V1.questionV1;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuestionRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired QuestionRepository questionRepository;

    @Before
    public void makeTestcase(){
        /*
        여기는 tc 만드는 과정
         */
        String content = "뜨거운지라, 능히 오직 동력은 피고 용감하고 수 같은 속에서 사막이다. 구하지 수 원대하고, 소담스러운 아니다. 못하다 청춘이 가진 인생의 어디 수 가치를 이것이다. 하는 위하여, 없는 따뜻한 풀이 용기가 인생을 보이는 아니다. 불어 물방아 밝은 청춘의 곧 인간의 끓는다. 인간은 보이는 생생하며, 뿐이다. 우리의 얼마나 얼마나 옷을 사랑의 굳세게 운다. 끝에 소금이라 이상의 우는 얼음에 아름다우냐? 미인을 불어 이상은 봄바람이다. 청춘의 우는 보이는 이것을 봄바람이다. 피고 할지니, 바로 청춘에서만 용기가 것이다.\n" +
                "\n" +
                "동산에는 타오르고 그러므로 거선의 얼음이 교향악이다. 이것은 새가 있는 그들에게 황금시대다. 온갖 청춘을 아니더면, 때까지 청춘에서만 있으랴? 가는 두손을 불어 하여도 사랑의 곳이 위하여서. 바이며, 가장 없으면, 작고 거선의 가는 것이다. 그것은 같이 이것이야말로 있는 황금시대의 얼마나 방황하여도, 듣는다. 내려온 없으면, 무엇을 수 힘있다. 주는 희망의 하여도 가는 찾아 것이다. 희망의 보이는 인생에 이것이다. 있는 동력은 가진 이상을 간에 생의 이것이다.\n" +
                "\n" +
                "아름답고 바로 이것을 바이며, 생명을 것은 품고 청춘에서만 투명하되 봄바람이다. 얼음에 타오르고 동산에는 봄바람이다. 대한 어디 할지라도 그들은 따뜻한 것은 길을 약동하다. 몸이 예가 얼마나 가슴에 더운지라 스며들어 없으면 뼈 사막이다. 과실이 오아이스도 품으며, 자신과 얼마나 못할 것은 생생하며, 위하여서. 바이며, 그들에게 전인 것이다. 고행을 같은 이상의 석가는 이는 그리하였는가? 이것이야말로 아니한 낙원을 그리하였는가? 붙잡아 가치를 끓는 보는 살 천자만홍이 하는 것이다.";
        Member mem = new Member();
        mem.setName("주석");
        mem.setUserType( UserType.LOCAL );
        mem.setUser_ID("testMan");
        mem.setAddress( new Address() ) ;
        mem.setPhone_Num( "010-1111-1111");
        mem.setEmail("test@test.test");
        memberRepository.save( mem ); // 멤 저장 cascade 안킴.

        questionV1 qv1 = new questionV1();
        qv1.setContents( content );
        LocalDateTime dateTime = LocalDateTime.now();
        qv1.setMember( mem );
        qv1.setWrittenDate( Timestamp.valueOf( dateTime ) );

        questionV1 qv2 = new questionV1();
        qv2.setMember( mem );
        LocalDateTime dateTime2 = LocalDateTime.now();
        qv2.setWrittenDate( Timestamp.valueOf( dateTime2 ) );
        qv2.setContents( qv1.getContents() );

        questionRepository.save( qv1 );
        questionRepository.save( qv2 );
    }
    @Test
    //@org.springframework.transaction.annotation.Transactional
    public void save() {
        /*
        여기는 tc 만드는 과정
         */
        String content = "뜨거운지라, 능히 오직 동력은 피고 용감하고 수 같은 속에서 사막이다. 구하지 수 원대하고, 소담스러운 아니다. 못하다 청춘이 가진 인생의 어디 수 가치를 이것이다. 하는 위하여, 없는 따뜻한 풀이 용기가 인생을 보이는 아니다. 불어 물방아 밝은 청춘의 곧 인간의 끓는다. 인간은 보이는 생생하며, 뿐이다. 우리의 얼마나 얼마나 옷을 사랑의 굳세게 운다. 끝에 소금이라 이상의 우는 얼음에 아름다우냐? 미인을 불어 이상은 봄바람이다. 청춘의 우는 보이는 이것을 봄바람이다. 피고 할지니, 바로 청춘에서만 용기가 것이다.\n" +
                "\n" +
                "동산에는 타오르고 그러므로 거선의 얼음이 교향악이다. 이것은 새가 있는 그들에게 황금시대다. 온갖 청춘을 아니더면, 때까지 청춘에서만 있으랴? 가는 두손을 불어 하여도 사랑의 곳이 위하여서. 바이며, 가장 없으면, 작고 거선의 가는 것이다. 그것은 같이 이것이야말로 있는 황금시대의 얼마나 방황하여도, 듣는다. 내려온 없으면, 무엇을 수 힘있다. 주는 희망의 하여도 가는 찾아 것이다. 희망의 보이는 인생에 이것이다. 있는 동력은 가진 이상을 간에 생의 이것이다.\n" +
                "\n" +
                "아름답고 바로 이것을 바이며, 생명을 것은 품고 청춘에서만 투명하되 봄바람이다. 얼음에 타오르고 동산에는 봄바람이다. 대한 어디 할지라도 그들은 따뜻한 것은 길을 약동하다. 몸이 예가 얼마나 가슴에 더운지라 스며들어 없으면 뼈 사막이다. 과실이 오아이스도 품으며, 자신과 얼마나 못할 것은 생생하며, 위하여서. 바이며, 그들에게 전인 것이다. 고행을 같은 이상의 석가는 이는 그리하였는가? 이것이야말로 아니한 낙원을 그리하였는가? 붙잡아 가치를 끓는 보는 살 천자만홍이 하는 것이다.";
        Member mem = new Member();
        mem.setName("주석");
        mem.setUserType( UserType.LOCAL );
        mem.setUser_ID("testMan");
        mem.setAddress( new Address() ) ;
        mem.setPhone_Num( "010-1111-1111");
        mem.setEmail("test@test.test");
        memberRepository.save( mem ); // 멤 저장 cascade 안킴.

        memberRepository.flush();
        questionV1 qV1 = new questionV1();
        qV1.setContents( content );
        qV1.setMember( mem );
        LocalDateTime dateTime = LocalDateTime.now();
        qV1.setWrittenDate(Timestamp.valueOf(dateTime) );

        /*
        ----------------------------------------------
         */

        questionRepository.save( qV1 );


        assertNotNull( qV1.getQuestion_Id() );




    }


    @Test
    public void findlstByname() {

        //questionRepository.flush();

        Member member = memberRepository.findByuserId("testMan");
        List< questionV1 > lst = questionRepository.findlstByuser( member.getMember_ID() );
        //questionRepository.clear();
        assertEquals(2, lst.size() );

    }

    @Test
    public void findAll() {
        /*
        String content = "뜨거운지라, 능히 오직 동력은 피고 용감하고 수 같은 속에서 사막이다. 구하지 수 원대하고, 소담스러운 아니다. 못하다 청춘이 가진 인생의 어디 수 가치를 이것이다. 하는 위하여, 없는 따뜻한 풀이 용기가 인생을 보이는 아니다. 불어 물방아 밝은 청춘의 곧 인간의 끓는다. 인간은 보이는 생생하며, 뿐이다. 우리의 얼마나 얼마나 옷을 사랑의 굳세게 운다. 끝에 소금이라 이상의 우는 얼음에 아름다우냐? 미인을 불어 이상은 봄바람이다. 청춘의 우는 보이는 이것을 봄바람이다. 피고 할지니, 바로 청춘에서만 용기가 것이다.\n" +
                "\n" +
                "동산에는 타오르고 그러므로 거선의 얼음이 교향악이다. 이것은 새가 있는 그들에게 황금시대다. 온갖 청춘을 아니더면, 때까지 청춘에서만 있으랴? 가는 두손을 불어 하여도 사랑의 곳이 위하여서. 바이며, 가장 없으면, 작고 거선의 가는 것이다. 그것은 같이 이것이야말로 있는 황금시대의 얼마나 방황하여도, 듣는다. 내려온 없으면, 무엇을 수 힘있다. 주는 희망의 하여도 가는 찾아 것이다. 희망의 보이는 인생에 이것이다. 있는 동력은 가진 이상을 간에 생의 이것이다.\n" +
                "\n" +
                "아름답고 바로 이것을 바이며, 생명을 것은 품고 청춘에서만 투명하되 봄바람이다. 얼음에 타오르고 동산에는 봄바람이다. 대한 어디 할지라도 그들은 따뜻한 것은 길을 약동하다. 몸이 예가 얼마나 가슴에 더운지라 스며들어 없으면 뼈 사막이다. 과실이 오아이스도 품으며, 자신과 얼마나 못할 것은 생생하며, 위하여서. 바이며, 그들에게 전인 것이다. 고행을 같은 이상의 석가는 이는 그리하였는가? 이것이야말로 아니한 낙원을 그리하였는가? 붙잡아 가치를 끓는 보는 살 천자만홍이 하는 것이다.";
        Member mem = new Member();
        mem.setName("주석");
        mem.setUserType( UserType.LOCAL );
        mem.setUser_ID("testMan");
        mem.setAddress( new Address() ) ;
        mem.setPhone_Num( "010-1111-1111");
        mem.setEmail("test@test.test");
        memberRepository.save( mem ); // 멤 저장 cascade 안킴.

        questionV1 qv1 = new questionV1();
        qv1.setContents( content );
        LocalDateTime dateTime = LocalDateTime.now();
        qv1.setMember( mem );
        qv1.setWrittenDate( Timestamp.valueOf( dateTime ) );

        questionV1 qv2 = new questionV1();
        qv2.setMember( mem );
        LocalDateTime dateTime2 = LocalDateTime.now();
        qv2.setWrittenDate( Timestamp.valueOf( dateTime2 ) );
        qv2.setContents( qv1.getContents() );

        questionRepository.save( qv1 );
        questionRepository.save( qv2 );
        //questionRepository.flush();

         */
        Member mem = memberRepository.findByuserId("testMan");
        List< questionV1 > lst = questionRepository.findAll( mem.getMember_ID() );
        //questionRepository.clear();
        assertEquals(2, lst.size() );
    }
}