package com.pipe09.OnlineShop.Service;

//import com.pipe09.OnlineShop.Oauth.Attributes;

/*
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


 */