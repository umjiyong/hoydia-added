package com.ssafy.hoydia.controller;


import com.ssafy.hoydia.domain.Platform;
import com.ssafy.hoydia.domain.Role;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.LoginException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
@Api (value = "UserController", description = ("유저 컨트롤러"))
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;
    private final HttpSession httpSession;

    @PostMapping("/login")
    @ApiOperation(value="소셜 로그인 후 아이디 생성", notes = "제 곧 내")
    public Map login(User user) {



        User loginuser = userService.login(user.getId());

        if (loginuser == null) {
            throw new LoginException("로그인에서 심각한 오류가 발생했습니다. 재가입 요망");
        }
        String token = jwtService.create("userId",loginuser.getId(),"access-token");

        Map map = new HashMap();
        map.put("access-token",token);
        map.put("userId",loginuser.getId());

        return map;
    }

    @PostMapping
    @ApiOperation(value="유저 작성", notes = "프론트 DBtest만을 위한 임시 기능, 고마워할 것")
    public MessageResponseDto registUser(@RequestBody RegistUserRequestDto request){

        User user = User.createUser(
                request.getName(),
                request.getEmail(),
                request.getPlatform(),
                request.getRole());

        userService.regist(user);

        return new MessageResponseDto(user.getId());
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable("userId")String userId, @RequestBody UpdateUserRequestDto request){

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        boolean isMine = currentUid.equals(userId);

        if(!isMine) throw new UnauthorizedException("로그인 아이디와 요청 아이디가 일치하지 않습니다.");

        userService.update(userId, request.getNickname());

        return "Loc-Controller : "+userId;
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
        private String name;

        @NotBlank
        private String email;

        @NotBlank
        private Platform platform;

        @NotBlank
        private Role role;

    }


    @Data
    static class LoginRequestDto {
        @NotBlank
        private String userId;
    }

    @Data
    static class UpdateUserRequestDto {

        private String nickname;

    }


}
