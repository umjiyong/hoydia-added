package com.ssafy.hoydia.controller;


import com.ssafy.hoydia.domain.Gender;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.LoginException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public MessageResponseDto registUser(@RequestBody RegistUserRequestDto request){

        User user = User.createUser(
                request.getNickname(),
                request.getGender(),
                request.getBirth());

        userService.regist(user);

        return new MessageResponseDto("회원가입 완료");
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public MessageResponseDto deleteUser(@PathVariable("userId") String userId){

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        boolean isMine = currentUid.equals(userId);

        if(!isMine) throw new UnauthorizedException();

        User user = userService.searchById(userId);

        userService.delete(userId);
        return new MessageResponseDto("회원 탈퇴가 완료되었습니다.");

    }

    @Data
    static class RegistUserRequestDto {

        @NotBlank
        private String nickname;

        @NotBlank
        private Gender gender;

        @NotBlank
        private Integer birth;

    }

    @Data
    static class LoginRequestDto {
        @NotBlank
        private String userId;
    }


}
