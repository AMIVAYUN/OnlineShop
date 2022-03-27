package com.pipe09.OnlineShop.Oauth;

import com.pipe09.OnlineShop.Domain.Member.Mem_status;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.RoleType;
import com.pipe09.OnlineShop.Domain.Member.UserType;
import com.pipe09.OnlineShop.Domain.Shoplist.ShopCart;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Getter
public class Attributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public Attributes(Map<String,Object>attributes,String nameAttributeKey,String name,String email){
        this.attributes=attributes;
        this.email=email;
        this.name=name;
        this.nameAttributeKey=nameAttributeKey;
    }
    public static Attributes of(String registrationId,String usernameAttributeName,Map<String,Object>attributes){
        if("kakao".equals(registrationId)){
            return ofKaKao("id",attributes);
        }
        return ofGoogle(usernameAttributeName,attributes);
    }
    //구글
    public static Attributes ofGoogle(String usernameAttributeName,Map<String,Object>attributes){
        return Attributes.builder().name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }
    //카카오
    private static Attributes ofKaKao(String usernameAttributeName,Map<String,Object>attributes){
        Map<String,Object> kakaoAccount=(Map<String,Object>)attributes.get("kakao_account");
        Map<String,Object> kakaoProfile=(Map<String,Object>)kakaoAccount.get("profile");
        return Attributes.builder().name((String)kakaoProfile.get("nickname"))
                .email((String)kakaoAccount.get("email"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName).build();
    }
    public Member toMem(String registrationId){
        Member goomem=new Member();
        goomem.setName(name);
        goomem.setEmail(email);
        if(registrationId.equals("google")){
            goomem.setUserType(UserType.GOOGLE);
        }else{
            goomem.setUserType(UserType.KAKAO);
        }
        goomem.setUser_ID(email);

        goomem.setDate(LocalDate.now());
        goomem.setStat(Mem_status.ACTIVATED);
        goomem.setShopCart(new ShopCart());
        goomem.setRoleType(RoleType.USER);
        return goomem;
    }
}
