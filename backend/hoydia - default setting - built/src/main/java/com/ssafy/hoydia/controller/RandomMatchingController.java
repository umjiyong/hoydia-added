package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
@Slf4j
public class RandomMatchingController {

    private final MatchingService matchingService;

    private final JwtService jwtService;



    @PostMapping
    @ApiOperation(value="매칭 시작", notes = "매칭을 시작하는 관리자 전용 기능(예정)")
    public MessageResponseDto initMatching () {

        String rest = matchingService.matchingStart();

        return new MessageResponseDto ("이번 타임의 매칭 완료.");

    }


    @PutMapping("/{matchingNoteId}")
    @ApiOperation(value="양측의 매칭 수락 또는 거절 확인 후 변경 및 삭제 ", notes = "매칭의 수락 여부 수정 매칭노트 id를 path로 permit여부 (true/false) body로 요청")
    public MessageResponseDto permitCheck (@PathVariable("matchingNoteId") String matchingNoteId , @RequestBody boolean permit) {


     matchingService.permitCheckedCheck(matchingNoteId , jwtService.getUserId(), permit);


        return new MessageResponseDto ("수락여부가 반영 되었습니다.");
    }













}
