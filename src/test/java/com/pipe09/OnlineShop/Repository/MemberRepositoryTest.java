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
@Transactional
public class MemberRepositoryTest {

    @Autowired MemberRepository Repository;

    @Test
    public void 멤버_등록과수정(){
        Member assignMember=new Member();
        assignMember.setName("윤주석");
        assignMember.setPhone_Num("010-3141-5278");
        assignMember.setRoleType(RoleType.ADMIN);

        Long id =Repository.save(assignMember);
        assertEquals("id가 서로 같아야 합니다.",assignMember.getMember_ID(),id);
        assertEquals("번호가 같아야 합니다",Repository.findById(id).getPhone_Num(),assignMember.getPhone_Num());

    }

}