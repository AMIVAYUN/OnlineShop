package com.pipe09.OnlineShop.Service;

import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.SessionUser;
import com.pipe09.OnlineShop.Oauth.Attributes;
import com.pipe09.OnlineShop.Repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Oauth2Service implements OAuth2UserService<OAuth2UserRequest,OAuth2User> {
    private final MemberRepository repository;
    @Getter
    private final HttpSession httpSession;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request)throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User=delegate.loadUser(request);

        String registratioId=request.getClientRegistration().getRegistrationId();

        String userNameAttributeName=request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Attributes attributes=Attributes.of(registratioId,userNameAttributeName,oAuth2User.getAttributes());
//저장
        Member member=saveOrUpdate(attributes,registratioId);

        httpSession.setAttribute("user",new SessionUser(member.getName(),member.getEmail(),registratioId));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes.getAttributes(), attributes.getNameAttributeKey()
        );
    }
    private Member saveOrUpdate(Attributes attributes,String registratioId){
        try{
            Member member=repository.findByEmailinOauth(attributes.getEmail());
            member=member.updateName(attributes.getName());
            return member;
        }catch (EmptyResultDataAccessException e){
            Member member=attributes.toMem(registratioId);
            repository.save(member);
            return member;
        }


    }
}
