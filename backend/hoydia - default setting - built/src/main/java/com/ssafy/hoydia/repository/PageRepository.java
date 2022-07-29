package com.ssafy.hoydia.repository;

import com.ssafy.hoydia.domain.MatchingNote;
import com.ssafy.hoydia.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class PageRepository {

    private final EntityManager em;

    public String regist(Page page) {

        em.persist(page);

        return "Loc-Repository : "+page.getId();

    }

    public Page findById(String id) {

        return em.find(Page.class,id);

    }


    public List<Page> findByDiary(String diaryId) { // 해당 다이어리의 모든 페이지를 가져옴

        List<Page> pages = em.createQuery("SELECT p FROM Page p WHERE p.diary.id = :diary_id",Page.class)
                .setParameter("diary_id",diaryId)
                .getResultList();

        return pages;

    }



    public void delete (String id) {

        em.remove(findById(id));

    }

}
