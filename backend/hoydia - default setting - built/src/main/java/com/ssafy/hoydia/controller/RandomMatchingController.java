package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.MatchingNote;
import com.ssafy.hoydia.domain.Page;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
@Slf4j
@Api(value = "RandomMatchingController", description = ("랜덤 매칭 컨트롤러"))
public class RandomMatchingController {

    private final MatchingService matchingService;

    private final MatchingNoteService matchingNoteService;

    private final JwtService jwtService;


    @PostMapping
    @ApiOperation(value = "매칭 시작", notes = "매칭을 시작하는 관리자 전용 기능(예정)")
    public MessageResponseDto initMatching() {

        String rest = matchingService.matchingStart();

        return new MessageResponseDto("이번 타임의 매칭 완료.");

    }

    @GetMapping("/{matchingNoteId}")
    @ApiOperation(value = "매칭노트 확인", notes = "id에 해당하는 매칭노트를 가져옴 urI에 pathVariable로 request")
    public ResultDto readMatchingNoteIdById(@PathVariable("matchingNoteId") String matchingNoteId) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        MatchingNote matchingNote = matchingNoteService.searchById(matchingNoteId);

        boolean isMine = currentUid.equals(matchingNote.getOwnerId()) || currentUid.equals(matchingNote.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        return new ResultDto(new ReadMatchingNoteResponseDto(matchingNote));

    }


    @PutMapping("/{matchingNoteId}")
    @ApiOperation(value = "양측의 매칭 수락 또는 거절 확인 후 변경 및 삭제 ", notes = "매칭의 수락 여부 수정 매칭노트 id를 path로 permit여부 (true/false) body로 요청")
    public MessageResponseDto permitCheck(@PathVariable("matchingNoteId") String matchingNoteId, @RequestBody UpdateMatchingNoteRequestDto request) {


        matchingService.permitCheckedCheck(matchingNoteId, jwtService.getUserId(), request.isPermit());


        return new MessageResponseDto("수락여부가 반영 되었습니다.");
    }


    @Data
    static class UpdateMatchingNoteRequestDto {

        boolean permit;

    }

    @Data
    @AllArgsConstructor
    static class ReadMatchingNoteResponseDto {

        private String id;
        private User user;
        private LocalDateTime regTime;
        private String ownerId;
        private String pairId;
        private String ownerQuestion;
        private String pairQuestion;
        private String ownerAnswer;
        private String pairAnswer;
        private int ownerPermit;
        private int pairPermit;


        public ReadMatchingNoteResponseDto(MatchingNote matchingNote) {

            this.id = matchingNote.getId();
            this.user = matchingNote.getUser();
            this.regTime = matchingNote.getRegTime();
            this.ownerId = matchingNote.getOwnerId();
            this.pairId = matchingNote.getPairId();
            this.ownerQuestion = matchingNote.getOwnerQuestion();
            this.pairQuestion = matchingNote.getPairQuestion();
            this.ownerAnswer = matchingNote.getOwnerAnswer();
            this.pairAnswer = matchingNote.getPairAnswer();
            this.ownerPermit = matchingNote.getOwnerPermit();
            this.pairPermit = matchingNote.getPairPermit();

        }
    }


}
