package com.ssafy.hoydia.config.auth.dto;

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

    @Builder
    public OAuthAttributes(Map <String,Object> userAttributes, String nameAttributeKey ,String name, String email) {
        this.userAttributes = userAttributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of (String registrationId, String userNameAttributeName, Map <String, Object> userAttributes) {
       return ofSocial (userNameAttributeName, userAttributes);
    }
    // 반환 정보가 map이기에 값 하나하나 반환해준다.

    private static OAuthAttributes ofSocial (String userNameAttributeName, Map<String, Object> userAttributes) {

        return OAuthAttributes.builder()
                .name((String) userAttributes.get("name"))
                .email((String) userAttributes.get("email"))
                .userAttributes(userAttributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    public User toEntity() {
        return User.createUser(name,email, Role.USER);
    }

}
