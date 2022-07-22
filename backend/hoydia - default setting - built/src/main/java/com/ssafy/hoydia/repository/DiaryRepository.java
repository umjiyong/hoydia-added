package com.ssafy.hoydia.repository;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class DiaryRepository {

    private final EntityManager em;

    public String regist(Diary diary) {

        em.persist(diary);

        return diary.getId();

    }

    private Object findById(String id) {

        return em.find(Diary.class,id);

    }


    public List<Diary> findByUser(String id) { // 유저의 모든 일기를 가져옴

        List<Diary> diaries = em.createQuery("SELECT d FROM Diary d WHERE d.user_id = :user_id",Diary.class)
                .setParameter("user_id",id)
                .getResultList();

        return diaries;

    }



    public void delete (String id) {

        em.remove(findById(id));

    }



}
