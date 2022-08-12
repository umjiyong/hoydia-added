package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.*;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class MatchingService {

    private final UserRepository userRepository;

    private final DiaryRepository diaryRepository;

    private final NoteRepository noteRepository;

    private final MatchingNoteRepository matchingNoteRepository;

    private final NoticeRepository noticeRepository;

    @Transactional
    public String matchingStart() { // id순으로 매칭해서 note를 만들고 홀수 일 경우 마지막 순서인 친구는 대기.

        List<Note> noteList = noteRepository.findAllNoteOrderById();

        for( int i = 0 ; i < noteList.size()/2 ; i++) {

            Note note1 = noteList.get(i*2);
            Note note2 = noteList.get(i*2+1);

            MatchingNote matchingNote = MatchingNote.builder()
                    .user(note1.getUser())
                    .ownerId(note1.getUser().getId())
                    .ownerAnswer(note1.getAnswer())
                    .ownerQuestion(note1.getQuestion())
                    .pairId(note2.getUser().getId())
                    .pairAnswer(note2.getAnswer())
                    .pairQuestion(note2.getQuestion())
                    .build();

            matchingNoteRepository.regist(matchingNote);

            sendMatchingNotice(note1.getUser() , note2.getUser() , "매칭중! 매칭노트 : " + matchingNote.getId() , "님과 매칭이 연결되었습니다." );


            noteRepository.delete(note1.getId());
            noteRepository.delete(note2.getId());

        }

        if (noteList.size()%2 == 0) return null;

        else {
            Note rest = noteList.get(noteList.size() - 1);

            Notice restNote = Notice.builder()
                    .user(rest.getUser())
                    .title("매칭 연장")
                    .content("인원 수가 맞지 않아 매칭되지 않았어요 ㅠㅠ 금방 찾아 드릴테니 조금만 더 기다려주세요!")
                    .build();

            noticeRepository.regist(restNote);

            return rest.getId();
        }
    }

    @Transactional
    public String permitCheckedCheck(String matchingNoteId , String userId , boolean permit) { // 매칭노트 아이디로 체크


        MatchingNote matchingNote = matchingNoteRepository.findById(matchingNoteId);

        if (userId.equals(matchingNote.getOwnerId())) { // 요청 보낸 사람이 owner
            if (permit) {
                matchingNote.setOwnerPermit(1);
            }
            else matchingNote.setOwnerPermit(2);
        }

        else if (userId.equals(matchingNote.getPairId())){  // 요청 보낸 사람이 pair

            if (permit) {
                matchingNote.setPairPermit(1);
            }
            else matchingNote.setPairPermit(2);

        }

        else {
            throw new InvalidApproachException("자신의 매칭 노트가 아닌 요청입니다.");
        }


        if (matchingNote.getOwnerPermit() != 0 && matchingNote.getPairPermit() != 0) { // 둘 다 반응을 했을 때

            if(matchingNote.getOwnerPermit() == 1 && matchingNote.getPairPermit() == 1){ // 둘 다 수락인 경우
                Diary diary = Diary.builder()
                        .user(matchingNote.getUser())
                        .title("제목")
                        .ownerId(matchingNote.getOwnerId())
                        .pairId(matchingNote.getPairId())
                        .build();

                diaryRepository.regist(diary);

                sendMatchingNotice(matchingNote.getUser() , noteRepository.findById(matchingNote.getPairId()).getUser() , "교환 일기 만들기 성공!" , "님과 다이어리가 만들어 졌어요." );

            }

            else {

                sendMatchingNotice(matchingNote.getUser() , noteRepository.findById(matchingNote.getPairId()).getUser() , "교환 일기 만들기 실패!" , "님과 매칭 중 한 분이 거절하였습니다 ㅠ_ㅠ" );

            }

            matchingNoteRepository.delete(matchingNoteId);

            return "매칭 결과가 나왔습니다.";

        }

        return "수락/거절 반영 완료";

    }

    @Transactional
    public void sendMatchingNotice (User user1, User user2, String title , String content) {

        Notice notice1 = Notice.builder()
                .user(user1)
                .title(title)
                .content(user2.getNickname()+content)
                .build();

        Notice notice2 = Notice.builder()
                .user(user2)
                .title(title)
                .content(user1.getNickname()+content)
                .build();

        noticeRepository.regist(notice1);
        noticeRepository.regist(notice2);

    }


}
