package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Long save(Member member){
        validateduplicateMember(member);
        member.Encodepwd(passwordEncoder);
        Long saveid =memberRepository.save(member);
        return saveid;
    }


    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public List<Member> findByname(String name){
        return memberRepository.findByName(name);
    }


    private void validateduplicateMember(Member member) {
        List<Member> findMembers=memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("중복 회원 입니다.");
        }
    }
    //SSR 로그인 로직
    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        Member member=memberRepository.findByuserId(user_id);
        System.out.println(member.getRoleType());
        if(member==null){
            throw new UsernameNotFoundException(user_id);
        }
        //login method
        return User.builder().username(member.getUser_ID()).password(member.getPwd()).roles().roles(member.getRoleType().toString()).build();
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
