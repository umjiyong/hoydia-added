package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
@Slf4j
public class RandomMatchingController {

    private final MatchingService matchingService;

    private final DiaryService diaryService;

    private final UserService userService;

    private final NoteService noteService;

    private final NoticeService noticeService;

    private final MatchingNoteService matchingNoteService;



    @PostMapping
    @ApiOperation(value="매칭 시작", notes = "매칭을 시작하는 관리자 전용 기능(예정)")
    public String initMatching () {

        String rest = matchingService.matchingStart();



       return null;

    }















}
