package com.ssafy.hoydia.repository;


import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class UserRepository {

    private final EntityManager em;

    public User regist(User user) { // 등록 성공시 Id 반환

        em.persist(user);

        return user;
    }

    public User findById(String id){  // User를 암호화 된 id로 찾는다. Match되는 User가 없는 Id라면 null 을 return.

        return em.find(User.class,id);

    }

    public List<User> findAllUserOrderById(){
        return em.createQuery("SELECT u from User u ORDER BY u.id DESC", User.class).getResultList();
    }

    public User findByEmail(String email) {

        List<User> user = em.createQuery("SELECT u FROM User u WHERE u.email = :email",User.class)
                .setParameter("email",email).getResultList();


        if (user.size()==0) return null;

        return user.get(0);

    }

    public void delete(String id){

        em.remove(findById(id));

    }


}
