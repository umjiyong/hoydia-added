package com.ssafy.hoydia.repository;


import com.ssafy.hoydia.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor

public class UserRepository {

    private final EntityManager em;

    public String regist(User user) {

        em.persist(user);

        return user.getId();
    }


}
