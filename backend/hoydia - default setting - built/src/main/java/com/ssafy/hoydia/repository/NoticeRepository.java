package com.ssafy.hoydia.repository;

import com.ssafy.hoydia.domain.Notice;
import com.ssafy.hoydia.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class NoticeRepository {

    private final EntityManager em;

    public Long regist(Notice notice) {

        em.persist(notice);

        return notice.getId();

    }

    public Notice findById(Long id) {

        return em.find(Notice.class,id);

    }


    public List<Notice> findByUserId(String userId) { // 해당 다이어리의 모든 페이지를 가져옴

        List<Notice> notices = em.createQuery("SELECT n FROM Notice n WHERE n.user.id = :user_id",Notice.class)
                .setParameter("user_id",userId)
                .getResultList();

        return notices;

    }



    public void delete (Long id) {

        em.remove(findById(id));

    }

}
