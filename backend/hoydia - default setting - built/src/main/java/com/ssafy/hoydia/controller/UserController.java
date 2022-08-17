package com.ssafy.hoydia.controller;


import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.Platform;
import com.ssafy.hoydia.domain.Role;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.LoginException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
@Api (value = "UserController", description = ("유저 컨트롤러"))
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;
    private final HttpSession httpSession;


    @PostMapping
    @ApiOperation(value="유저 작성", notes = "프론트 DB test만을 위한 임시 기능, 고마워할 것 키키, 특이사항 : parameter 중 role은 security 특성 때문에 ROLE_GUEST가 추천 parameter 값으로 되어있는데 그냥 USER나 GUEST 넣어야 함")
    public MessageResponseDto registUser(@RequestBody RegistUserRequestDto request){

        User user = User.builder()
                .id(request.getId())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .role(Role.USER)
                .platform(request.getPlatform())
                .build();

        userService.regist(user);

        return new MessageResponseDto(user.getId());
    }

    @GetMapping("/list")
    @ApiOperation(value="모든 유저 확인", notes = "모든 유저의 리스트를 가져오는 관리자 전용(예정) 기능")
    public ResultDto readAllUser(){          // 모든 유저를 읽어오는 관리자 전용 기능
        List<ReadUserResponseDto> userList = userService.searchAllUserOrderById().stream().map(user -> new ReadUserResponseDto(user)).collect(Collectors.toList());
        return new ResultDto(userList);
    }

    @GetMapping
    @ApiOperation(value="유저 확인", notes = "현재 접속해 있는 유저의 정보 확인")
    public ResultDto readUser() {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        return new ResultDto(new ReadUserResponseDto(userService.searchById(currentUid)));

    }

    @PutMapping("/{userId}")
    @ApiOperation(value="유저 업데이트", notes = "닉네임을 Body part에 넣어 request")
    public String updateUser(@PathVariable("userId")String userId, @RequestBody UpdateUserRequestDto request){

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        boolean isMine = currentUid.equals(userId);

        if(!isMine) throw new UnauthorizedException("로그인 아이디와 요청 아이디가 일치하지 않습니다.");

        userService.update(userId,
                request.getNickname());

        return "Loc-UserController : "+userId;
    }

    // 회원 탈퇴
    @DeleteMapping
    @ApiOperation(value="회원 탈퇴", notes = "로그인 된 아이디를 회원 탈퇴")
    public MessageResponseDto deleteUser(){

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        userService.delete(currentUid);
        return new MessageResponseDto("회원 탈퇴가 완료되었습니다.");

    }

    @Data
    static class RegistUserRequestDto {

        @NotBlank
        private String id;

        private String nickname;

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

    @Data
    @AllArgsConstructor
    static class ReadUserResponseDto {

        private String id;

        private String nickname;

        private String email;

        private Platform platform;

        private Role role;

        private String gender;
        public ReadUserResponseDto(User user) {

            this.id = user.getId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.platform = user.getPlatform();
            this.role = user.getRole();
            this.gender = user.getGender();


        }
    }

    @PostMapping("/login")
    @ApiOperation(value="테스트용 로그인 기능 액세스 토큰 발급", notes = "FE에서 DB Test 편의성을 위해 임시로 존재하는 기능 / DB 존재하는 userId만 넣으면 로그인 성공 및 Authorized된 access-token 발급")
    public Map login(String userId) {


        User loginuser = userService.login(userId);

        if (loginuser == null) {
            throw new LoginException("로그인에서 심각한 오류가 발생했습니다. 재가입 요망");
        }
        String token = jwtService.create("userId",loginuser.getId(),"access-token");

        Map map = new HashMap();
        map.put("access-token",token);
        map.put("userId",loginuser.getId());

        return map;
    }



}
