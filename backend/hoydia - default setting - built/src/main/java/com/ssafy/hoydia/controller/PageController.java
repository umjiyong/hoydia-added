package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.domain.Content;
import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.Page;
import com.ssafy.hoydia.domain.Title;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/page")
@Slf4j
@Api(value = "PageController", description = ("페이지 컨트롤러(일기장의 각 페이지)"))
public class PageController {

    private final JwtService jwtService;
    private final UserService userService;
    private final DiaryService diaryService;
    private final PageService pageService;
    private final NoticeService noticeService;
    private final AwsS3Service awsS3Service;


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
                .titleFontStyle(request.getTitleFontStyle())
                .titleFontSize(request.getTitleFontSize())
                .content(request.getContent())
                .contentFontStyle(request.getContentFontStyle())
                .contentFontSize(request.getContentFontSize())
                .bgmPath(request.getBgmPath())
                .locationx(request.getLocationx())
                .locationy(request.getLocationy())
                .build();

        pageService.regist(page);

        return new CreatePageResponseDto(page.getId(),page.getRegTime());
    }



    @GetMapping("/{id}")
    @ApiOperation(value="페이지 확인", notes = "id에 해당하는 페이지를 가져옴 urI에 pathVariable로 request")
    public ResultDto readPageById(@PathVariable("id") String id) {

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

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }

        List<ReadPageResponseDto> pageList = new ArrayList<>();

        pageList = pageService.searchByDiaryId(diaryId).stream().map(page -> new ReadPageResponseDto(page)).collect(Collectors.toList());

        return new ResultDto(pageList);

    }

    @PutMapping("/send/{pageId}")
    @ApiOperation(value="작성한 일기 페이지를 전송", notes = "pageId를 PathVariable로 받고 diaryId를 함께 body에 request")
    public CreatePageResponseDto sendCreatedPage(@PathVariable("pageId") String pageId , @RequestBody @Valid SendCreatedPageRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Diary diary = diaryService.searchById(request.getDiaryId());

        boolean isMine = currentUid.equals(diary.getOwnerId())||currentUid.equals(diary.getPairId()); // 현재 로그인 된 id와 사용자 2의 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 일기가 아닙니다.");
        }


        Page page = pageService.searchById(pageId);



        if (!diary.isOwn()) {
            noticeService.sendNoticeAlone(userService.searchById(diary.getOwnerId()), "일기 교환 완료!", userService.searchById(diary.getPairId()).getNickname() + "님과의 교환일기가 도착했습니다!");
        }
        else {
            noticeService.sendNoticeAlone(userService.searchById(diary.getPairId()),"일기 교환 완료!", userService.searchById(diary.getOwnerId()).getNickname()+"님과의 교환일기가 도착했습니다!");
        }


        diaryService.diaryOwnShift(request.getDiaryId());; // 페이지가 위치한 다이어리의 소유주 상태를 전환해줌.

        return new CreatePageResponseDto(page.getId(),page.getRegTime());
    }

    @PutMapping("/image/{pageId}")
    @ApiOperation(value="작성한 페이지에 이미지를 전송", notes = "pageId를 PathVariable로 받고 body에 이미지를 request")
    public String uploadImage(@PathVariable("pageId") String pageId ,@RequestPart(value = "file") MultipartFile multipartFile) {

        String imgPath = awsS3Service.uploadFileV1("image", multipartFile);

        pageService.setImgPath(pageId,imgPath);

        return imgPath;
    }

    @PutMapping("/bgm/{pageId}")
    @ApiOperation(value="작성한 페이지의 bgmPath를 수정", notes = "pageId를 PathVariable로 받고 body에 bgmpath를 request")
    public String uploadImage(@PathVariable("pageId") String pageId , String bgmPath ) {

        pageService.setBgmPath(pageId,bgmPath);

        return bgmPath;
    }

    @PutMapping("/loc/{pageId}")
    @ApiOperation(value="작성한 페이지의 장소를 수정", notes = "pageId를 PathVariable로 받고 body에 loc를 request")
    public String uploadImage(@PathVariable("pageId") String pageId , LocRequestDto request ) {

        pageService.setLocation(pageId, request.locationx, request.locationy);

        return "장소 설정 완료";
    }

    @PutMapping("/update/{pageId}")
    @ApiOperation(value="페이지를 업데이트", notes = "pageId에 대응되는 page의 value들을 수정. (variable은 body로 request) id는 pathVariable로 request - 죽은 기능(update 하지 않기로 함)")
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

        pageService.update(id,
                request.getTitle(),
                request.getTitleFontStyle(),
                request.getTitleFontSize(),
                request.getContent(),
                request.getContentFontStyle(),
                request.getContentFontSize(),
                request.getBgmPath(),
                request.getLocationx(),
                request.getLocationy());

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

        private String title;

        private String titleFontStyle;

        private String titleFontSize;

        private String content;

        private String contentFontStyle;

        private String contentFontSize;

        private String bgmPath;

        private String imgPath;

        private String locationx;

        private String locationy;

    }

    @Data
    static class SendCreatedPageRequestDto {

        @NotBlank
        private String diaryId;

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

        private String title;

        private String titleFontStyle;

        private String titleFontSize;

        private String content;

        private String contentFontStyle;

        private String contentFontSize;

        private String bgmPath;

        private String imgPath;

        private String locationx;

        private String locationy;


        public ReadPageResponseDto(Page page) {

            this.id = page.getId();
            this.diaryId = page.getDiary().getId();
            this.regTime = page.getRegTime();
            this.title = page.getTitle();
            this.titleFontStyle = page.getTitleFontStyle();
            this.titleFontSize = page.getTitleFontSize();
            this.content = page.getContent();
            this.contentFontStyle = page.getContentFontStyle();
            this.contentFontSize = page.getContentFontSize();
            this.bgmPath = page.getBgmPath();
            this.imgPath = page.getImgPath();
            this.locationx = page.getLocationx();
            this.locationy = page.getLocationy();

        }
    }


    @Data
    static class UpdatePageRequestDto {

        private String title;

        private String titleFontStyle;

        private String titleFontSize;

        private String content;

        private String contentFontStyle;

        private String contentFontSize;

        private String bgmPath;

        private String imgPath;

        private String locationx;

        private String locationy;

    }

    @Data
    static class LocRequestDto {

        private String locationx;

        private String locationy;

    }



}
