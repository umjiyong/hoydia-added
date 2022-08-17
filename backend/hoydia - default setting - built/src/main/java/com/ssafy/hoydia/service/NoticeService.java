package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.Note;
import com.ssafy.hoydia.domain.Notice;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long regist (Notice notice) {

        noticeRepository.regist(notice);

        return notice.getId();
    }

    public Notice searchById (Long id){

        Notice notice = noticeRepository.findById(id);

        return notice;
    }

    public List<Notice> searchByUserId(String userId) {

        List<Notice> noticeList = noticeRepository.findByUserId(userId);

        return noticeList;
    }

    @Transactional
    public String update (Long id, String title, String content) {

        Notice notice = noticeRepository.findById(id);

        if(notice == null) {
            throw new InvalidApproachException("잘못된 접근입니다.");
        }
        else{

            notice.setTitle(title);

            notice.setContent(content);

            return "Loc-noticeService : "+notice.getId();
        }

    }

    @Transactional
    public void delete (Long id) {

        noticeRepository.delete(id);

    }

    @Transactional
    public void sendNotice (User user1, User user2, String title , String content) {

        Notice notice1 = Notice.builder()
                .user(user1)
                .title(title)
                .content(content)
                .build();

        Notice notice2 = Notice.builder()
                .user(user2)
                .title(title)
                .content(content)
                .build();

        noticeRepository.regist(notice1);
        noticeRepository.regist(notice2);

    }
    @Transactional
    public void sendNoticeAlone (User user1, String title , String content) {

        Notice notice1 = Notice.builder()
                .user(user1)
                .title(title)
                .content(content)
                .build();

        noticeRepository.regist(notice1);

    }



}
