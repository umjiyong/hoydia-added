package com.ssafy.hoydia.service;


import com.ssafy.hoydia.client.ClientGoogle;
import com.ssafy.hoydia.config.auth.AuthToken;
import com.ssafy.hoydia.config.auth.AuthTokenProvider;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.AuthRequest;
import com.ssafy.hoydia.dto.AuthResponse;
import com.ssafy.hoydia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoogleAuthService { // public class GoogleAuthService

    private final ClientGoogle clientGoogle;
    private final AuthTokenProvider authTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        User googleUser = clientGoogle.getUserData(authRequest.getAccessToken()); // userData 담기
        String id = googleUser.getId();
        User user = userRepository.findById(id);
        boolean isNewUser = false;

        AuthToken appToken = authTokenProvider.createUserAppToken(id); // 신규 토큰 생성

        if (user == null) {
            userRepository.regist(googleUser);
            isNewUser = true;
        }

        return AuthResponse.builder() // /auth/kakao와 /auth/google의 응답의 body로 AccessToken(AppToken)을 보내주기위해 builder 사용
                .id(id)
                .isNewMember(isNewUser)
                .build();
    }
}