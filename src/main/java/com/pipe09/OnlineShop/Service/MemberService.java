package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Member member){
        validateduplicateMember(member);
        Long saveid =memberRepository.save(member);
        return saveid;
    }


    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public List<Member> findByname(String name){
        return memberRepository.findByName(name);
    }

    public Member findByID(String saveid){
        return memberRepository.findById(saveid);
    }

    private void validateduplicateMember(Member member) {
        List<Member> findMembers=memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("중복 회원 입니다.");
        }
    }


}
