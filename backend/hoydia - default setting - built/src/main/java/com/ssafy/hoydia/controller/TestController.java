package com.ssafy.hoydia.controller;


import com.ssafy.hoydia.config.auth.dto.SessionUser;
import com.ssafy.hoydia.domain.Role;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Slf4j
public class TestController {

    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping
    public void test123(){          // 등록 테스트
        SessionUser test1 = (SessionUser) httpSession.getAttribute("user");

        userService.regist(User.createUser(test1.getName(), test1.getEmail(), Role.USER));

    }


}
