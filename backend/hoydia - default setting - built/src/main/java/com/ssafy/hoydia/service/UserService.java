package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String regist (User user) {

       userRepository.regist(user);

       return "Loc-Service : "+user.getId();
    }

}
