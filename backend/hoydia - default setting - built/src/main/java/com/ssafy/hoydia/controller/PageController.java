package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.domain.Content;
import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.Page;
import com.ssafy.hoydia.domain.Title;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.DiaryService;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.PageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
@Slf4j
@Api(value = "PageController", description = ("페이지 컨트롤러(일기장의 각 페이지)"))
public class PageController {

    private final JwtService jwtService;
    private final DiaryService diaryService;
    private final PageService pageService;


    @PostMapping
    @ApiOperation(value="일기 페이지 작성", notes = "Page에 들어갈 parameter들과 Authorization을 위한 diaryId를 함께 body에 request")
    public CreatePageResponseDto createPage(@RequestBody @Valid CreatePageRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Diary diary = diaryService.searchById(request.getDiaryId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }


        Page page = Page.builder()
                .diary(diary)
                .title(request.getTitle())
                .content(request.getContent())
                .bgmPath(request.getBgmPath())
                .location(request.getLocation())
                .build();

        pageService.regist(page);

        return new CreatePageResponseDto(page.getId(),page.getRegTime());
    }

    @GetMapping("/{id}")
    @ApiOperation(value="페이지 확인", notes = "id에 해당하는 페이지를 가져옴 urI에 pathVariable로 request")
    public ResultDto readDiaryById(@PathVariable("id") String id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Page page = pageService.searchById(id);

        Diary diary = diaryService.searchById(page.getDiary().getId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        return new ResultDto(new ReadPageResponseDto(page));

    }

    @GetMapping("/diary/{diaryId}")
    @ApiOperation(value="페이지가 쓰여질 다이어리의 모든 페이지 리스트 확인", notes = "diaryId에 해당하는 다이어리의 모든 페이지 리스트를 가져옴 urI에 pathVariable로 request")
    public ResultDto readPageByDiaryId(@PathVariable("diaryId") String diaryId) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("인증 실패");

        String currentUid = jwtService.getUserId();

        Diary diary = diaryService.searchById(diaryId);

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        List<ReadPageResponseDto> pageList = new ArrayList<>();

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        pageList = pageService.searchByDiaryId(diaryId).stream().map(page -> new ReadPageResponseDto(page)).collect(Collectors.toList());

        return new ResultDto(pageList);

    }

    @PutMapping("/{pageId}")
    @ApiOperation(value="페이지를 업데이트", notes = "pageId에 대응되는 page의 value들을 수정. (variable은 body로 request) id는 pathVariable로 request")
    public MessageResponseDto updatePage(@PathVariable("pageId") String id, @RequestBody @Valid UpdatePageRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Page page = pageService.searchById(id);

        Diary diary = diaryService.searchById(page.getDiary().getId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        pageService.update(id,request.getTitle(),request.getContent(),request.getBgmPath(), request.getLocation());

        return new MessageResponseDto("수정 완료");
    }

    @DeleteMapping("{pageId}")
    @ApiOperation(value="페이지를 삭제", notes = "pageId에 해당하는 page 제거 / id는 pathVariable로 request")
    public MessageResponseDto deletePage(@PathVariable("pageId") String id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Page page = pageService.searchById(id);

        Diary diary = diaryService.searchById(page.getDiary().getId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        pageService.delete(id);

        return new MessageResponseDto("삭제 완료");

    }



    @Data
    static class CreatePageRequestDto {

        @NotBlank
        private String diaryId; //소유권 체크용

        @NotBlank
        private Title title;

        private Content content;

        private String bgmPath;

        private String location;

    }

    @Data
    @AllArgsConstructor
    static class CreatePageResponseDto {
        private String id;

        private LocalDateTime regTime;
    }


    @Data
    @AllArgsConstructor
    static class ReadPageResponseDto {

        private String id;

        private String diaryId;

        private LocalDateTime regTime;

        private Title title;

        private Content content;

        private String bgmPath;

        private String location;


        public ReadPageResponseDto(Page page) {

            this.id = page.getId();
            this.diaryId = page.getDiary().getId();
            this.regTime = page.getRegTime();
            this.title = page.getTitle();
            this.content = page.getContent();
            this.bgmPath = page.getBgmPath();
            this.location = page.getLocation();

        }
    }


    @Data
    static class UpdatePageRequestDto {

        @NotBlank
        private Title title;

        private Content content;

        private String bgmPath;

        private String location;


    }





}
