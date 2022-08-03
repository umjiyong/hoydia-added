package com.ssafy.hoydia.config.auth.dto;

import com.ssafy.hoydia.domain.Platform;
import com.ssafy.hoydia.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private Platform platform;

    public SessionUser (User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.platform = user.getPlatform();
    }


}
