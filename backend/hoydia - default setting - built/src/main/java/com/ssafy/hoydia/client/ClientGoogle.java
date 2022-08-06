package com.ssafy.hoydia.client;

import com.ssafy.hoydia.domain.Platform;
import com.ssafy.hoydia.domain.Role;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.GoogleUserResponse;
import com.ssafy.hoydia.exception.TokenValidFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientGoogle implements ClientProxy {

    private final WebClient webClient;


    @Override
    public User getUserData(String accessToken) {
        GoogleUserResponse googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        return User.builder()
                .id(googleUserResponse.getSub())
                .nickname(googleUserResponse.getName())
                .email(googleUserResponse.getEmail())
                .platform(Platform.GOOGLE)
                .role(Role.USER)
                .build();
    }
}