package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Member.Address;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.RoleType;
import com.pipe09.OnlineShop.Domain.Member.UserType;
import com.pipe09.OnlineShop.Service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {


    /*
    public Member findByName(String name)
    public Member findByuserId(String id)
    public List<Member> findByuserIdlist(String id)
    public List<Member> findAll(int offset,int limit)
     */
    @Autowired MemberRepository repository;
    Long saveId;

    @Before
    public void forUnit(){
        Member tmember = Member.createMember("testMan","testMan","010-1111-1111","123456","test@test.test",new Address());
        this.saveId = repository.save( tmember );
    }

    @Test
    @DisplayName( " findByName test " )
    public void findByNameTest(){
        //이름으로 찾았을 때, testMan이 있어야 하며, Before에서 저장한 아이디 값과 같아야 한다.
        Member fmember = repository.findByName( "testMan" );
        assertEquals( true, fmember.getMember_ID() == this.saveId );
        assertEquals( "testMan", fmember.getName() );


    }

    @Test
    @DisplayName( " findByuserId test " )
    public void findByUserIdTest(){
        //아이디를 찾았을 때, testMan이 있어야 하며, Before에서 저장한 값과 같아야 한다.
        Member fmember = repository.findByName( "testMan" );
        assertEquals( true, fmember.getMember_ID() == this.saveId );
        assertEquals( "testMan", fmember.getUser_ID() );

    }

    @Test
    @DisplayName( " findByuserIdLst test " )
    public void findByUserIdlistTest(){
        Member tmember = Member.createMember("testMan","testMan","010-1111-1111","123456","test@test.test",new Address());
        Long newId = repository.save( tmember );
        repository.flush();
        //같은 값 두개를 넣었으므로 2개를 리턴해야 하며, 각 개체의 아이디는 testMan 이어야 한다.
        List< Member > memberList = repository.findByuserIdlist( "testMan" );

        assertEquals(2,memberList.size() );
        for( int i = 0 ; i < memberList.size() ; i++){
            assertEquals( true, memberList.get( i ).getUser_ID().equals( "testMan" ) );
        }


    }

    @Test
    @DisplayName( " findAllwithofflim test ")
    public void findAllwithofflimTest(){
        List< Member > memberList = repository.findAll( 0 , 30 );
        //찾은 데이터 열의 길이는 limit 이하여야 하며, 0이 아닐시에 값이 있어야 한다.
        assertEquals( true, memberList.size() <= 30 );
        if( memberList.size() > 0 ){
            assertNotEquals( null, memberList.get( 0 ).getName() );
        }

    }

}