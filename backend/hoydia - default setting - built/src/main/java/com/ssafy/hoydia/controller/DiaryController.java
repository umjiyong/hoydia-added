package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.Font;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.LoginException;
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
    @ApiOperation(value="일기 작성", notes = "사용자 두 명에 해당하는 고유 id와 color들을 body에 request 후 작성.")
    public CreateDiaryResponseDto createDiary (@RequestBody @Valid CreateDiaryRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        if(userService.searchById(request.getPairId()) == null)
            throw new InvalidApproachException("존재하지 않는 친구 코드입니다.");

        Diary diary = Diary.builder()
                .user(userService.searchById(jwtService.getUserId()))
                .ownerId(jwtService.getUserId())
                .pairId(request.getPairId())
                .diaryColor(request.getDiaryColor())
                .buttonColor(request.getButtonColor())
                .build();

        diaryService.regist(diary);

        return new CreateDiaryResponseDto(diary.getId(),diary.getRegTime());
    }

    @GetMapping("/list")
    @ApiOperation(value="모든 다이어리 확인", notes = "모든 다이어리 리스트를 가져오는 관리자 전용(예정) 기능")
    public ResultDto readAllDiary(){          // 모든 다이어리를 읽어오는 관리자 전용 기능
        List<ReadDiaryResponseDto> diaryList = diaryService.searchAllDiaryOrderByRegTime().stream().map(diary -> new ReadDiaryResponseDto(diary)).collect(Collectors.toList());
        return new ResultDto(diaryList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value="다이어리 확인", notes = "id에 해당하는 다이어리를 가져옴 urI에 pathVariable로 request")
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
    @ApiOperation(value="해당 유저의 모든 다이어리 확인", notes = "userId에 해당하는 유저의 모든 다이어리 리스트를 가져옴 urI에 pathVariable로 request")
    public ResultDto readDiaryByUserId(@PathVariable("userId") String userId) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("인증 실패");

        String currentUid = jwtService.getUserId();

        boolean isMine = currentUid.equals(userId);

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        List<ReadDiaryResponseDto> diaryList = new ArrayList<>();

        diaryList = diaryService.searchByUserId(userId).stream().map(diary -> new ReadDiaryResponseDto(diary)).collect(Collectors.toList());

        return new ResultDto(diaryList);

    }

    @PutMapping("/{diaryId}")
    @ApiOperation(value="다이어리를 업데이트", notes = "diaryId에 해당하는 diary의 title,color,drawn 등을 수정 가능 (variable은 body로 request) id는 pathVariable로 request")
    public MessageResponseDto updateDiary(@PathVariable("diaryId") String id, @RequestBody @Valid UpdateDiaryRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Diary diary = diaryService.searchById(id);

        boolean isMine = currentUid.equals(diary.getUser().getId());

        if(!isMine) throw new UnauthorizedException("본인의 일기가 아닙니다.");

        diaryService.update(id,
                request.getTitle(),
                request.getDiaryColor(),
                request.getButtonColor(),
                request.getFont(),
                request.getDrawn());

        return new MessageResponseDto("수정 완료");
    }


    @DeleteMapping("{diaryId}")
    @ApiOperation(value="다이어리를 삭제", notes = "diaryId에 해당하는 diary 제거 / id는 pathVariable로 request")
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
        private String pairId;

        private String diaryColor;

        private String buttonColor;

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

        private String title;

        private String diaryColor;

        private String buttonColor;

        private Font font;

        private Integer drawn;

        public ReadDiaryResponseDto(Diary diary) {

            this.id = diary.getId();
            this.userId = diary.getUser().getId();
            this.userName = diary.getUser().getNickname();
            this.regTime = diary.getRegTime();
            this.ownerId = diary.getOwnerId();
            this.pairId = diary.getPairId();
            this.own = diary.isOwn();
            this.title = diary.getTitle();
            this.diaryColor = diary.getDiaryColor();
            this.buttonColor = diary.getButtonColor();
            this.font = diary.getFont();
            this.drawn = diary.getDrawn();

        }
    }

    @Data
    static class UpdateDiaryRequestDto {

        @NotBlank
        private String title;

        @NotBlank
        private String diaryColor;

        @NotBlank
        private String buttonColor;

        @NotBlank
        private Font font;

        @NotBlank
        private int drawn;

    }

}
