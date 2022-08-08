package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.Note;
import com.ssafy.hoydia.domain.Page;
import com.ssafy.hoydia.dto.MessageResponseDto;
import com.ssafy.hoydia.dto.ResultDto;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.exception.UnauthorizedException;
import com.ssafy.hoydia.service.JwtService;
import com.ssafy.hoydia.service.NoteService;
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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
@Slf4j
@Api(value = "NoteController", description = ("노트 컨트롤러(아직 매칭이 되기 전 노트들)"))
public class NoteController {

    private final JwtService jwtService;

    private final UserService userService;

    private final NoteService noteService;

    @PostMapping
    @ApiOperation(value="노트 작성", notes = "노트에 들어가야할 사항들을 body에 담아 request")
    public CreateNoteResponseDto createNote(@RequestBody @Valid CreateNoteRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        Note note = Note.builder()
                .user(userService.searchById(jwtService.getUserId()))
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build();

        noteService.regist(note);

        return new CreateNoteResponseDto(note.getId(),note.getRegTime());
    }

    @GetMapping("/{id}")
    @ApiOperation(value="노트 확인", notes = "id에 해당하는 노트를 가져옴 urI에 pathVariable로 request")
    public ResultDto readNoteById(@PathVariable("id") String id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Note note = noteService.searchById(id);

        boolean isMine = currentUid.equals(note.getUser().getId()); // 현재 로그인 된 id와 노트 주인 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 노트가 아닙니다.");
        }

        return new ResultDto(new ReadNoteResponseDto(note));

    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value="해당 유저의 모든 노트 확인", notes = "userId에 해당하는 유저의 모든 노트 리스트를 가져옴 urI에 pathVariable로 request")
    public ResultDto readNoteByUserId(@PathVariable("userId") String userId) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("인증 실패");

        String currentUid = jwtService.getUserId();

        boolean isMine = currentUid.equals(userId);

        if (!isMine) {
            throw new UnauthorizedException("본인의 노트가 아닙니다.");
        }

        List<ReadNoteResponseDto> noteList = new ArrayList<>();

        noteList = noteService.searchByUserId(userId).stream().map(note -> new ReadNoteResponseDto(note)).collect(Collectors.toList());

        return new ResultDto(noteList);

    }

    @PutMapping("/{noteId}")
    @ApiOperation(value="노트를 업데이트", notes = "noteId에 해당하는 note의 property들을 수정 가능 (variable은 body로 request) id는 pathVariable로 request")
    public MessageResponseDto updateNote(@PathVariable("noteId") String id, @RequestBody @Valid UpdateNoteRequestDto request) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        boolean isMine = currentUid.equals(id);

        if (!isMine) {
            throw new UnauthorizedException("본인의 노트가 아닙니다.");
        }

        noteService.update(id,
                request.getQuestion(),
                request.getAnswer());

        return new MessageResponseDto("수정 완료");
    }

    @DeleteMapping("{noteId}")
    @ApiOperation(value="노트를 삭제", notes = "noteId에 해당하는 note 제거 / id는 pathVariable로 request")
    public MessageResponseDto deleteNote(@PathVariable("noteId") String id) {

        if (!jwtService.isValidUser())
            throw new InvalidApproachException("사용자 인증 실패");

        String currentUid = jwtService.getUserId();

        Note note = noteService.searchById(id);

        boolean isMine = currentUid.equals(note.getUser().getId()); // 현재 로그인 된 id와 노트 주인 id check

        if (!isMine) {
            throw new UnauthorizedException("본인의 노트가 아닙니다.");
        }

        noteService.delete(id);

        return new MessageResponseDto("삭제 완료");

    }


    @Data
    static class CreateNoteRequestDto {

        @NotBlank
        private String question;

        @NotBlank
        private String answer;

    }

    @Data
    @AllArgsConstructor
    static class CreateNoteResponseDto {

        private String id;

        private LocalDateTime regTime;
    }

    @Data
    @AllArgsConstructor
    static class ReadNoteResponseDto {
        private String id;

        private String userId;

        private LocalDateTime regTime;

        private String question;

        private String answer;
        public ReadNoteResponseDto(Note note) {

            this.id = note.getId();

            this.userId = note.getUser().getId();

            this.regTime = note.getRegTime();

            this.question = note.getQuestion();

            this.answer = note.getAnswer();


        }
    }

    @Data
    static class UpdateNoteRequestDto {

        private String question;

        private String answer;

    }

}
