package com.ssafy.hoydia.client;

import com.ssafy.hoydia.domain.Platform;
import com.ssafy.hoydia.domain.Role;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.KakaoUserResponse;
import com.ssafy.hoydia.exception.TokenValidFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientKakao implements ClientProxy {

    private final WebClient webClient;

    @Override
    public User getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me") // KAKAO의 유저 정보 받아오는 url
                .headers(h -> h.setBearerAuth(accessToken)) // JWT 토큰을 Bearer 토큰으로 지정
                .retrieve()
                // 아래의 onStatus는 error handling
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(KakaoUserResponse.class) // KAKAO의 유저 정보를 넣을 Dto 클래스
                .block();

        return User.builder()
                .id(String.valueOf(kakaoUserResponse.getId()))
                .nickname(kakaoUserResponse.getProperties().getNickname())
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .gender(kakaoUserResponse.getKakaoAccount().getGender())
                .platform(Platform.KAKAO)
                .role(Role.USER)
                .build();
    }
}