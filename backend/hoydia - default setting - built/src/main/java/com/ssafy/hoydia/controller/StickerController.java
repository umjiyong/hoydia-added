package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.domain.*;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.DiaryService;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.PageService;
import com.ssafy.hoydia.service.StickerService;
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
@RequestMapping("/sticker")
@Slf4j
@Api(value = "StickerController", description = ("스티커 컨트롤러"))
public class StickerController {

    private final JwtService jwtService;

    private final DiaryService diaryService;
    private final PageService pageService;

    private final StickerService stickerService;



    @PostMapping
    @ApiOperation(value="스티커 등록", notes = "스티커가 위치하는 pageId와 스티커에 들어가야할 사항들(종류,위치,path 등)을 body에 담아 request")
    public CreateStickerResponseDto createSticker(@RequestBody @Valid CreateStickerRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Page page = pageService.searchById(request.pageId);

        Diary diary = diaryService.searchById(page.getDiary().getId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        Sticker sticker = Sticker.builder()
                .page(pageService.searchById(request.pageId))
                .type(request.getType())
                .posX(request.getPosX())
                .posY(request.getPosY())
                .path(request.getPath())
                .build();

        stickerService.regist(sticker);

        return new CreateStickerResponseDto(sticker.getId(),sticker.getRegTime(),sticker.getPath());
    }

    @GetMapping("/{id}")
    @ApiOperation(value="스티커 확인", notes = "id에 해당하는 스티커를 가져옴 urI에 pathVariable로 request")
    public ResultDto readStickerById(@PathVariable("id") String id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Sticker sticker = stickerService.searchById(id);

        Diary diary = diaryService.searchById(pageService.searchById(sticker.getPage().getId()).getDiary().getId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        return new ResultDto(new ReadStickerResponseDto(sticker));

    }

    @GetMapping("/page/{pageId}")
    @ApiOperation(value="해당 페이지의 모든 스티커 확인", notes = "pageId에 해당하는 페이지의 모든 스티커 리스트를 가져옴 urI에 pathVariable로 request")
    public ResultDto readStickerByPageId(@PathVariable("pageId") String pageId) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Page page = pageService.searchById(pageId);

        Diary diary = diaryService.searchById(page.getDiary().getId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        List<ReadStickerResponseDto> stickerList = new ArrayList<>();

        stickerList = stickerService.searchByPageId(pageId).stream().map(sticker -> new ReadStickerResponseDto(sticker)).collect(Collectors.toList());

        return new ResultDto(stickerList);

    }

    @PutMapping("/{stickerId}")
    @ApiOperation(value="스티커를 업데이트", notes = "stickerId에 대응되는 sticker의 value들을 수정. (variable은 body로 request) id는 pathVariable로 request")
    public MessageResponseDto updateSticker(@PathVariable("stickerId") String id, @RequestBody @Valid UpdateStickerRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Sticker sticker = stickerService.searchById(id);

        Diary diary = diaryService.searchById(pageService.searchById(sticker.getPage().getId()).getDiary().getId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        stickerService.update(id,
                request.getType(),
                request.getPosX(),
                request.getPosY()
                );

        return new MessageResponseDto("수정 완료");
    }

    @DeleteMapping("{stickerId}")
    @ApiOperation(value="스티커를 삭제", notes = "stickerId에 해당하는 sitcker 제거 / id는 pathVariable로 request")
    public MessageResponseDto deletePage(@PathVariable("stickerId") String id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Sticker sticker = stickerService.searchById(id);

        Diary diary = diaryService.searchById(pageService.searchById(sticker.getPage().getId()).getDiary().getId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        stickerService.delete(id);

        return new MessageResponseDto("삭제 완료");

    }


    @Data
    static class CreateStickerRequestDto {

        @NotBlank
        private String pageId; //스티커가 표시될 페이지

        private String type;

        private String posX;

        private String posY;

        private String path;

    }

    @Data
    @AllArgsConstructor
    static class CreateStickerResponseDto {
        private String id;

        private LocalDateTime regTime;

        private String path;
    }

    @Data
    @AllArgsConstructor
    static class ReadStickerResponseDto {

        private String id;

        private String pageId;

        private LocalDateTime regTime;

        private String type;

        private String posX;

        private String posY;

        private String path;

        public ReadStickerResponseDto(Sticker sticker) {

            this.id = sticker.getId();
            this.pageId = sticker.getPage().getId();
            this.regTime = sticker.getRegTime();
            this.type = sticker.getType();
            this.posX = sticker.getPosX();
            this.posY = sticker.getPosY();
            this.path = sticker.getPath();

        }
    }

    @Data
    static class UpdateStickerRequestDto {

        private String type;

        private String posX;

        private String posY;

    }

}
