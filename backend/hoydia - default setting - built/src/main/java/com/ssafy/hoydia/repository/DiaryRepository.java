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

        return "Loc-Repository : "+diary.getId();

    }

    public Diary findById(String id) {

        return em.find(Diary.class,id);

    }

    public List<Diary> findAllDiaryOrderByRegTime(){
        return em.createQuery("SELECT d from Diary d ORDER BY d.regTime DESC", Diary.class).getResultList();
    }


    public List<Diary> findByUser(String userId) { // 유저의 모든 일기를 가져옴

        List<Diary> diaries1 = em.createQuery("SELECT d FROM Diary d WHERE d.ownerId = :user_id AND d.own = 1 ",Diary.class)
                .setParameter("user_id",userId)
                .getResultList();

        List<Diary> diaries2 = em.createQuery("SELECT d FROM Diary d WHERE d.pairId = :user_id AND d.own = 0",Diary.class)
                .setParameter("user_id",userId)
                .getResultList();

        diaries1.addAll(diaries2);

        return diaries1;

    }



    public void delete (String id) {

        em.remove(findById(id));

    }



}
