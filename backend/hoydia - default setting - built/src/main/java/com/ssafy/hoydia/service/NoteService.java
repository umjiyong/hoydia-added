package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.MatchingNote;
import com.ssafy.hoydia.domain.Note;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public String regist (Note note) {

        noteRepository.regist(note);

        return "Loc-Service : "+note.getId();
    }

    public Note searchById (String id){

        Note note = noteRepository.findById(id);

        return note;
    }

    public List<Note> searchByUserId(String userId) {

        List<Note> noteList = noteRepository.findByUser(userId);

        return noteList;
    }

    @Transactional
    public String update (String id, String question, String answer) {

        Note note = noteRepository.findById(id);

        if(note == null) {
            throw new InvalidApproachException("잘못된 접근입니다.");
        }
        else{

            note.setQuestion(question);

            note.setAnswer(answer);

            note.setRegTime(LocalDateTime.now());

            return "Loc-Service : "+note.getId();
        }

    }



    @Transactional
    public void delete (String id) {

        noteRepository.delete(id);

    }



}
