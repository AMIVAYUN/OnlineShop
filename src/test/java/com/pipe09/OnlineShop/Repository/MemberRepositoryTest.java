package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.RoleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class MemberRepositoryTest {

    @Autowired MemberRepository Repository;

    @Test
    @Transactional
    public void 멤버_등록과수정(){
        Member assignMember=Member.createMember("wntjr","wntjr","010");
        System.out.println(assignMember.getMember_ID());
        String id =Repository.save(assignMember);
        /**
        assertEquals("id가 서로 같아야 합니다.",assignMember.getMember_ID(),id);
        assertEquals("번호가 같아야 합니다",Repository.findById(id).getPhone_Num(),assignMember.getPhone_Num());
         */

    }

}