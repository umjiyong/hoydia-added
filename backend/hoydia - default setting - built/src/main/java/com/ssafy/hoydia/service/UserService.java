package com.ssafy.hoydia.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.OauthTokenDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.LoginException;
import com.ssafy.hoydia.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserService {

    private final UserRepository userRepository;

    /* 로그인 기능은 OAUTH 적용 후 수정 */
    public User login(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new LoginException("존재하지 않는 유저입니다.");
        }

        else {
                return user;
            }

    }

    @Transactional
    public String regist (User user) {

       userRepository.regist(user);

       return "Loc-Service : "+user.getId();
    }

    public User searchById(String id) {

        return userRepository.findById(id);

    }

    public User searchByEmail(String email) {

        return userRepository.findByEmail(email);

    }


    @Transactional
    public void update(String id, String nickname)  {        // 닉네임만 변경 가능.

        User user = userRepository.findById(id);

        if( user == null ){
            throw new InvalidApproachException("등록되지 않은 사용자입니다.");
        }
        else {

            user.setNickname(nickname);

        }
    }


    @Transactional
    public void delete(String id) {

        userRepository.delete(id);
    }

    @Transactional
    public OauthTokenDto getAccessTokenKakao (String code) {

        RestTemplate restTemplate = new RestTemplate(); // 통신에 좋은 객체인 RestTemplate이라고 한다..

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        //Http 헤더

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "c1a2b19a1c67960871b2d1c9d080d585");
        params.add("redirect_uri", "http://localhost:3000/kakaoLogin");
        params.add("code", code);
        params.add("client_secret", "Z81XEhK8E16NT1jbkqxZ3atfJvOGhBye"); // 생략 가능!
        //Http 바디가 될 부분

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        //Http 헤더와 파라미터 저장

        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);
        // 응답 값이 JSON 형식이므로 String 형식의 객체만 받는다.

        ObjectMapper objectMapper = new ObjectMapper();
        OauthTokenDto oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthTokenDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;



    }




}
