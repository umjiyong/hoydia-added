package com.ssafy.hoydia.controller;


import com.ssafy.hoydia.domain.Gender;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.exception.LoginException;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public Map login(@RequestBody @Valid LoginRequestDto request) {

        User user = userService.login(request.getUserId());

        if (user == null) {
            throw new LoginException("로그인에서 심각한 오류가 발생했습니다. 재가입 요망");
        }
        String token = jwtService.create("userId",user.getId(),"access-token");

        Map map = new HashMap();
        map.put("access-token",token);
        map.put("userId",user.getId());

        return map;
    }

    @PostMapping
    public TempResponseDto registUser(@RequestBody RegistUserRequestDto request){

        User user = User.createUser(
                request.nickname,
                request.gender,
                request.birth);

        userService.regist(user);

        return new TempResponseDto("회원가입 완료");
    }

    @Data
    static class RegistUserRequestDto {

        private String id;
        private String nickname;
        private Gender gender;
        private Integer birth;

    }

    @Data
    @AllArgsConstructor
    static class TempResponseDto {
        private String msg;
    }

    @Data
    static class LoginRequestDto {
        @NotBlank
        private String userId;
    }


}
