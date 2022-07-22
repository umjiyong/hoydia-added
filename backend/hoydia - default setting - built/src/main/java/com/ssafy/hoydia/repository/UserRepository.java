package com.ssafy.hoydia.repository;


import com.ssafy.hoydia.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor

public class UserRepository {

    private final EntityManager em;

    public String regist(User user) { // 등록 성공시 Id 반환

        em.persist(user);

        return user.getId();
    }

    private User findById(String id){  // User를 암호화 된 id로 찾는다. Match되는 User가 없는 Id라면 null 을 return.

        return em.find(User.class,id);

    }

    public void delete(String id){

        em.remove(findById(id));

    }


}
