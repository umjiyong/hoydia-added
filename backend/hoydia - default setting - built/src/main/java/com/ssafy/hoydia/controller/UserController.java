package com.ssafy.hoydia.controller;


import com.ssafy.hoydia.domain.Gender;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")

public class UserController {

    private final UserService userService;

    @PostMapping
    public TempResponseDto registUser(@RequestBody RegistUserRequestDto request){

        User user = User.createUser(
                request.id,
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


}
