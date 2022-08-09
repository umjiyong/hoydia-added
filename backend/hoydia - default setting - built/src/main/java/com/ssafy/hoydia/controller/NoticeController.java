package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.domain.Note;
import com.ssafy.hoydia.domain.Notice;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.NoticeService;
import com.ssafy.hoydia.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Slf4j
@Api(value = "NoticeController", description = ("유저에게 고지할 사항에 관한 컨트롤러"))
public class NoticeController {

    private final JwtService jwtService;

    private final UserService userService;
    private final NoticeService noticeService;

    @PostMapping
    @ApiOperation(value="알람 작성", notes = "유저에게 고지할 사항 작성 - 관리자 전용 기능(예정)")
    public MessageResponseDto createNotice(@RequestBody @Valid CreateNoticeRequestDto request) {

        Notice notice = Notice.builder()
                .user(userService.searchById(request.getUserId()))
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        noticeService.regist(notice);

        return new MessageResponseDto("알람 작성 완료");
    }


    @GetMapping
    @ApiOperation(value="로그인 유저의 모든 알람 확인", notes = "로그인되어 있는 유저의 모든 알람을 가져옴")
    public ResultDto readNotice() {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("인증 실패");

        String currentUid = jwtService.getUserId();

        List<ReadNoticeResponseDto> noticeList = new ArrayList<>();

        noticeList = noticeService.searchByUserId(currentUid).stream().map(notice -> new ReadNoticeResponseDto(notice)).collect(Collectors.toList());

        return new ResultDto(noticeList);

    }

    @PutMapping("/{noticeId}")
    @ApiOperation(value="알람을 업데이트", notes = "noticeId에 해당하는 notice를 수정 (body로 request) id는 pathVariable로 request - 관리자 전용 기능 (예정)")
    public MessageResponseDto updateNotice(@PathVariable("noticeId") Long id, @RequestBody @Valid UpdateNoticeRequestDto request) {

        noticeService.update(id,
                request.getTitle(),
                request.getContent());

        return new MessageResponseDto("수정 완료");
    }

    @DeleteMapping("{noticeId}")
    @ApiOperation(value="알람을 삭제", notes = "noticeId에 해당하는 notice 제거 / id는 pathVariable로 request - 접속자 본인의 알람이어야함")
    public MessageResponseDto deleteNotice(@PathVariable("noticeId") Long id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Notice notice = noticeService.searchById(id);

        boolean isMine = currentUid.equals(notice.getUser().getId()); // 현재 로그인 된 id와 노트 주인 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 알람이 아닙니다.");
        }

        noticeService.delete(id);

        return new MessageResponseDto("삭제 완료");

    }


    @Data
    static class CreateNoticeRequestDto {

        @NotBlank
        private String userId;

        @NotBlank
        private String title;

        private String content;

    }

    static class ReadNoticeResponseDto {
        private Long id;

        private LocalDateTime regTime;

        private String title;

        private String content;
        public ReadNoticeResponseDto(Notice notice) {

            this.id = notice.getId();
            this.regTime = notice.getRegTime();
            this.title = notice.getTitle();
            this.content = notice.getContent();

        }
    }

    @Data
    static class UpdateNoticeRequestDto {

        private String title;

        private String content;

    }

}
