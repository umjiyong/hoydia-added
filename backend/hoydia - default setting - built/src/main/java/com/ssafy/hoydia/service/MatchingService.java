package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.MatchingNote;
import com.ssafy.hoydia.domain.Note;
import com.ssafy.hoydia.repository.MatchingNoteRepository;
import com.ssafy.hoydia.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class MatchingService {

    private final NoteRepository noteRepository;

    private final MatchingNoteRepository matchingNoteRepository;


    @Transactional
    public String matchingStart() {

        List<Note> noteList = noteRepository.findAllNoteOrderById();

        for( int i = 0 ; i < noteList.size()/2 ; i++) {

            Note note1 = noteList.get(i*2);
            Note note2 = noteList.get(i*2+1);

            MatchingNote matchingNote = MatchingNote.builder()
                    .ownerId(note1.getId())
                    .ownerAnswer(note1.getAnswer())
                    .ownerQuestion(note1.getQuestion())
                    .pairId(note2.getId())
                    .pairAnswer(note2.getAnswer())
                    .pairQuestion(note2.getQuestion())
                    .build();

            matchingNoteRepository.regist(matchingNote);

            noteRepository.delete(note1.getId());
            noteRepository.delete(note2.getId());

        }

        if (noteList.size()/2 == 0) return null;

        else return noteList.get(noteList.size()-1).getId();

    }

    @Transactional
    private void permitCheckedCheck(String matchingNoteId) {

        MatchingNote matchingNote = matchingNoteRepository.findById(matchingNoteId);

        if (matchingNote.getOwnerPermit() != 0 && matchingNote.getPairPermit() != 0) {

            matchingNoteRepository.delete(matchingNoteId);

        }


    }



}
