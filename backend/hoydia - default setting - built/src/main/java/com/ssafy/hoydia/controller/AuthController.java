package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.config.auth.AuthTokenProvider;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.ApiResponse;
import com.ssafy.hoydia.dto.AuthRequest;
import com.ssafy.hoydia.dto.AuthResponse;
import com.ssafy.hoydia.dto.LoginResponse;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.LoginException;
import com.ssafy.hoydia.service.GoogleAuthService;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.KakaoAuthService;
import com.ssafy.hoydia.service.UserService;
import com.ssafy.hoydia.util.JwtHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Api(value = "AuthController", description = ("소셜 로그인 관련 컨트롤러"))
public class AuthController {


    private final UserService userService;

    private final KakaoAuthService kakaoAuthService;

    private final GoogleAuthService googleAuthService;

    private final JwtService jwtService;

    /**
     * KAKAO 소셜 로그인 기능
     *
     * @return ResponseEntity<AuthResponse>
     */
    @ApiOperation(value = "카카오 로그인", notes = "카카오 인가코드를 받아서 로그인해서 사용자 정보 받아 저장하고 회원가입 및 로그인 처리 -> 자체 액세스토큰 발급")
    @PostMapping(value = "/kakao")
    public Map kakaoAuthRequest(@RequestParam("code") String code) {

        AuthRequest authRequest = new AuthRequest(userService.getAccessTokenKakao(code).getAccess_token());

        String userId = kakaoAuthService.login(authRequest).getId();

        User loginUser = userService.login(userId);

        if (loginUser == null) {
            throw new LoginException("로그인에서 심각한 오류가 발생했습니다. 재가입 요망");
        }
        String token = jwtService.create("userId", loginUser.getId(), "access-token");

        Map map = new HashMap();
        map.put("access-token", token);
        map.put("userId", loginUser.getId());

        return map;
    }

    /**
     * GOOGLE 소셜 로그인 기능
     *
     * @return ResponseEntity<AuthResponse>
     */

    @ApiOperation(value = "구글 로그인", notes = "구글 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 회원가입 및 로그인 처리 -> 자체 액세스토큰 발급")
    @PostMapping(value = "/google")
    public Map googleAuthRequest(@RequestBody AuthRequest authRequest) {

        String userId = googleAuthService.login(authRequest).getId();
        User loginUser = userService.login(userId);

        if (loginUser == null) {
            throw new LoginException("로그인에서 심각한 오류가 발생했습니다. 재가입 요망");
        }
        String token = jwtService.create("userId", loginUser.getId(), "access-token");

        Map map = new HashMap();
        map.put("access-token", token);
        map.put("userId", loginUser.getId());

        return map;

    }


    @ApiOperation(value = "Jwt 토큰 잔여시간 갱신", notes = "1시간의 만료기간을 갱신해주고, 안된다면 Exception을 던짐;")
    @PostMapping(value = "/refresh")
    public Map authAccessTokenRefresh() {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        User loginUser = userService.login(currentUid);

        if (loginUser == null) {
            throw new LoginException("로그인에서 심각한 오류가 발생했습니다. 재가입 요망");
        }
        String token = jwtService.create("userId", loginUser.getId(), "access-token");

        Map map = new HashMap();
        map.put("access-token", token);
        map.put("userId", loginUser.getId());

        return map;

    }


    /**
     * 레거시 소셜 로그인 함수들.
     */
    @ApiOperation(value = "레거시 카카오 로그인", notes = "카카오 인가코드를 받아서 로그인해서 사용자 정보 받아 저장하고 앱의 토큰 반환")
    @PostMapping(value = "/kakao/legacytest")
    public ResponseEntity<AuthResponse> legacyKakaoAuthRequest(@RequestParam("code") String code) {

        AuthRequest authRequest = new AuthRequest(userService.getAccessTokenKakao(code).getAccess_token());

        return ApiResponse.success(kakaoAuthService.login(authRequest)); // body에 appToken 반환(response code 200)
    }


    @ApiOperation(value = "레거시 구글 로그인", notes = "구글 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 신환")
    @PostMapping(value = "/google/legacytest")
    public ResponseEntity<AuthResponse> legacyGoogleAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(googleAuthService.login(authRequest)); // body에 appToken 반환(response code 200)
    }
}



