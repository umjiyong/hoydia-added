package com.ssafy.hoydia.repository;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.MatchingNote;
import com.ssafy.hoydia.domain.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoteRepository {

    private final EntityManager em;

    public String regist(Note note) {

        em.persist(note);

        return "Loc-Repository : "+note.getId();
    }

    public Note findById (String id){

        return em.find(Note.class,id);
    }

    public List<Note> findByUser(String userId) { // 해당 유저의 Note를 가져옴

        List<Note> notes = em.createQuery("SELECT n FROM Note n WHERE n.user.id = :user_id",Note.class)
                .setParameter("user_id",userId)
                .getResultList();

        return notes;

    }

    public List<Note> findAllNoteOrderById(){
        return em.createQuery("SELECT n from Note n ORDER BY n.id DESC", Note.class).getResultList();
    }


    public void delete (String id) {

        em.remove(findById(id));

    }

}
