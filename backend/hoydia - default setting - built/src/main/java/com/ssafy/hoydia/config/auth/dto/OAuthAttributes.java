package com.ssafy.hoydia.config.auth.dto;

import com.ssafy.hoydia.domain.Platform;
import com.ssafy.hoydia.domain.Role;
import com.ssafy.hoydia.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> userAttributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private Platform platform;

    @Builder
    public OAuthAttributes(Map <String,Object> userAttributes, String nameAttributeKey ,String name, String email,Platform platform) {
        this.userAttributes = userAttributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.platform = platform;
    }

    public static OAuthAttributes of (String registrationId, String userNameAttributeName, Map <String, Object> userAttributes) {

        if("naver".equals(registrationId)) {
            return ofNaver("id",userAttributes);
        }

        if("kakao".equals(registrationId)) {
            return ofKakao("email",userAttributes);// email로 구분하고 있음
        }
        
        return ofGoogle (userNameAttributeName, userAttributes); // 배포 후 사실상 사용되지 않음
    }
    // 반환 정보가 map이기에 값 하나하나 반환해준다.

    private static OAuthAttributes ofGoogle (String userNameAttributeName, Map<String, Object> userAttributes) {

        return OAuthAttributes.builder()
                .name((String) userAttributes.get("name"))
                .email((String) userAttributes.get("email"))
                .platform(Platform.GOOGLE)
                .userAttributes(userAttributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    private static OAuthAttributes ofNaver (String userNameAttributeName, Map<String, Object> userAttributes) {

        Map<String, Object> response = (Map<String, Object>) userAttributes.get("response");
        // 구글과 다른이유는 네이버가 반환하는 데이터 형식이 JSON이기 떄문에

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .platform(Platform.NAVER)
                .userAttributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    private static OAuthAttributes ofKakao (String userNameAttributeName, Map<String, Object> userAttributes) {

        Map<String, Object> response = (Map<String, Object>) userAttributes.get("kakao_account");

        Map<String, Object> profile = (Map<String, Object>) response.get("profile");

        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))//수정해야함
                .email((String) response.get("email"))
                .platform(Platform.KAKAO)
                .userAttributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    public User toEntity() {
        return User.createUser(name,email,platform, Role.USER);
    }

}


