package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.Gender;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.LoginException;
import com.ssafy.hoydia.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


}
