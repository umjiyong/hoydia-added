package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.DiaryService;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@RequestMapping("/diary")
@Slf4j
@Api (value = "DiaryController", description = ("일기 컨트롤러"))
public class DiaryController {

    private final JwtService jwtService;
    private final DiaryService diaryService;
    private final UserService userService;

    @PostMapping
    @ApiOperation(value="일기 작성", notes = "사용자 두 명의 고유 id와 color를 body에 request 후 작성.")
    public CreateDiaryResponseDto createDiary(@RequestBody @Valid CreateDiaryRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        Diary diary = Diary.createDiary(
                userService.searchById(jwtService.getUserId()),
                request.getOwnerId(),
                request.getPairId(),
                true,
                request.getDiaryColor(),
                0
                );

        diaryService.regist(diary);

        return new CreateDiaryResponseDto(diary.getId(),diary.getRegTime());
    }

    @GetMapping("/list")
    public ResultDto readAllDiary(){          // 모든 다이어리를 읽어오는 관리자 전용 기능
        List<ReadDiaryResponseDto> diaryList = diaryService.searchAllDiaryOrderByRegTime().stream().map(diary -> new ReadDiaryResponseDto(diary)).collect(Collectors.toList());
        return new ResultDto(diaryList);
    }

    @GetMapping("/{id}")
    public ResultDto readDiaryById(@PathVariable("id") String id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Diary diary = diaryService.searchById(id);

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        return new ResultDto(new ReadDiaryResponseDto(diary));

    }

    @GetMapping("/user/{userId}")
    public ResultDto readDiaryByUserId(@PathVariable("userId") String userId) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("인증 실패");

        String currentUid = jwtService.getUserId();

        boolean isMine = currentUid.equals(userId);

        List<ReadDiaryResponseDto> diaryList = new ArrayList<>();

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        diaryList = diaryService.searchByUserId(userId).stream().map(diary -> new ReadDiaryResponseDto(diary)).collect(Collectors.toList());

        return new ResultDto(diaryList);

    }

    @PutMapping("/{diaryId}")
    public MessageResponseDto updateDiary(@PathVariable("diaryId") String id, @RequestBody @Valid UpdateDiaryRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Diary diary = diaryService.searchById(id);

        boolean isMine = currentUid.equals(diary.getUser().getId());

        if(!isMine) throw new UnauthorizedException("본인의 일기가 아닙니다.");

        diaryService.update(id,request.getColor());

        return new MessageResponseDto("수정 완료");
    }


    @DeleteMapping("{diaryId}")
    public MessageResponseDto deleteDiary(@PathVariable("diaryId") String id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Diary diary = diaryService.searchById(id);

        boolean isMine = currentUid.equals(diary.getUser().getId());

        if(!isMine) throw new UnauthorizedException("본인의 일기가 아닙니다.");

        diaryService.delete(id);

        return new MessageResponseDto("삭제 완료");

    }

    @Data
    static class CreateDiaryRequestDto {

        @NotBlank
        private String ownerId;

        @NotBlank
        private String pairId;

        @NotBlank
        private String diaryColor;
    }

    @Data
    @AllArgsConstructor
    static class CreateDiaryResponseDto {
        private String id;

        private LocalDateTime regTime;
    }

    @Data
    @AllArgsConstructor
    static class ReadDiaryResponseDto {
        private String id;

        private String userId;

        private String userName;

        private LocalDateTime regTime;

        private String ownerId;

        private String pairId;

        private boolean own;

        private String diaryColor;

        private Integer drawn;

        public ReadDiaryResponseDto(Diary diary) {

            this.id = diary.getId();
            this.userId = diary.getUser().getId();
            this.userName = diary.getUser().getNickname();
            this.regTime = diary.getRegTime();
            this.ownerId = diary.getOwnerId();
            this.pairId = diary.getPairId();
            this.own = diary.isOwn();
            this.diaryColor = diary.getDiaryColor();
            this.drawn = diary.getDrawn();

        }
    }

    @Data
    static class UpdateDiaryRequestDto {

        @NotBlank
        private String color;

    }

}
