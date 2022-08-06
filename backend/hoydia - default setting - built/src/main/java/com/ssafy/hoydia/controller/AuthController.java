package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.config.auth.AuthTokenProvider;
import com.ssafy.hoydia.dto.ApiResponse;
import com.ssafy.hoydia.dto.AuthRequest;
import com.ssafy.hoydia.dto.AuthResponse;
import com.ssafy.hoydia.service.GoogleAuthService;
import com.ssafy.hoydia.service.KakaoAuthService;
import com.ssafy.hoydia.service.UserService;
import com.ssafy.hoydia.util.JwtHeaderUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private final UserService userService;

    private final KakaoAuthService kakaoAuthService;
    private final GoogleAuthService googleAuthService;

    /**
     * KAKAO 소셜 로그인 기능
     * @return ResponseEntity<AuthResponse>
     */
    @ApiOperation(value = "카카오 로그인", notes = "카카오 인가코드를 받아서 로그인해서 사용자 정보 받아 저장하고 앱의 토큰 반환")
    @PostMapping(value = "/kakao")
    public ResponseEntity<AuthResponse> kakaoAuthRequest(@RequestParam("code") String code) {

        AuthRequest authRequest = new AuthRequest(userService.getAccessTokenKakao(code).getAccess_token());

        return ApiResponse.success(kakaoAuthService.login(authRequest)); // body에 appToken 반환(response code 200)
    }

    /**
     * GOOGLE 소셜 로그인 기능
     * @return ResponseEntity<AuthResponse>
     */
    @ApiOperation(value = "구글 로그인", notes = "구글 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 신환")
    @PostMapping(value = "/google")
    public ResponseEntity<AuthResponse> googleAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(googleAuthService.login(authRequest)); // body에 appToken 반환(response code 200)
    }

}