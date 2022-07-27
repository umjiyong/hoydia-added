package com.ssafy.hoydia.repository;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.MatchingNote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class MatchingNoteRepository {

    private final EntityManager em;

    public String regist(MatchingNote matchingNote) {

        em.persist(matchingNote);

        return "Loc-Repository : "+matchingNote.getId();

    }

    public MatchingNote findById(String id) {

        return em.find(MatchingNote.class,id);

    }


    public List<MatchingNote> findByUser(String userId) { // 해당 유저의 MatchingNote를 가져옴

        List<MatchingNote> matchingNotes = em.createQuery("SELECT m FROM MatchingNote m WHERE m.user.id = :user_id",MatchingNote.class)
                .setParameter("user_id",userId)
                .getResultList();

        return matchingNotes;

    }



    public void delete (String id) {

        em.remove(findById(id));

    }


}
