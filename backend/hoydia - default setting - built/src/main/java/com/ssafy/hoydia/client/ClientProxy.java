package com.ssafy.hoydia.client;

import com.ssafy.hoydia.domain.User;

public interface ClientProxy {

    User getUserData(String accessToken);
}