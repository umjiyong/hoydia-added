package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.MatchingNote;
import com.ssafy.hoydia.domain.Sticker;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.repository.MatchingNoteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class MatchingNoteService {

    private final MatchingNoteRepository matchingNoteRepository;

    @Transactional
    public String regist (MatchingNote matchingNote) {

       matchingNoteRepository.regist(matchingNote);

       return "Loc-Service : "+matchingNote.getId();
    }

    public MatchingNote searchById (String id){

        MatchingNote matchingNote = matchingNoteRepository.findById(id);

        return matchingNote;
    }

    public List<MatchingNote> searchByUserId(String user_id) {

        List<MatchingNote> matchingNoteList = matchingNoteRepository.findByUser(user_id);

        return matchingNoteList;
    }

    @Transactional
    public String update (String id, String ownerId, String ownerQuestion, String ownerAnswer, String pairId, String pairQuestion, String pairAnswer) {

        MatchingNote matchingNote = matchingNoteRepository.findById(id);

        if(matchingNote == null) {
            throw new InvalidApproachException("잘못된 접근입니다.");
        }
        else{

            matchingNote.setOwnerId(ownerId);
            matchingNote.setOwnerQuestion(ownerQuestion);
            matchingNote.setOwnerAnswer(ownerAnswer);

            matchingNote.setPairId(pairId);
            matchingNote.setPairQuestion(pairQuestion);
            matchingNote.setPairAnswer(pairAnswer);


            return "Loc-Service : "+matchingNote.getId();
        }

    }



    @Transactional
    public void delete (String id) {

        matchingNoteRepository.delete(id);

    }

}
