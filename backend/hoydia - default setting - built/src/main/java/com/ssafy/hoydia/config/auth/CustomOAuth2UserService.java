package com.ssafy.hoydia.config.auth;

import com.ssafy.hoydia.config.auth.dto.OAuthAttributes;
import com.ssafy.hoydia.config.auth.dto.SessionUser;
import com.ssafy.hoydia.domain.Role;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService <OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);



        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 어떤 플랫폼과 연동하는 것인지
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // User의 PK라고 생각하면 됨.

        OAuthAttributes userAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // 얻은 user의 attribute들을 저장할 class

        User user = saveOrUpdate(userAttributes);



        httpSession.setAttribute("user", new SessionUser(user)); // 세션에 사용자 정보 저장 (SessionUser라는 dto 사용)

        SessionUser test1 = (SessionUser) httpSession.getAttribute("user");
        System.out.println(test1.getName());
        System.out.println(test1.getEmail());


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
                userAttributes.getUserAttributes(),
                userAttributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes userAttributes) {

        System.out.println("cicicivalk");
        System.out.println("here + ::" + userAttributes.getEmail());

//        User user = userRepository.findByEmail(userAttributes.getEmail());


        User user = User.createUser(userAttributes.getName(), userAttributes.getEmail(), Role.USER);
//        if (user==null) {
            return user;
//        }
//            else return userRepository.regist(user);


    }



}
