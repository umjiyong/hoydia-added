package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.Notice;
import com.ssafy.hoydia.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void delete (Long id) {

        noticeRepository.delete(id);

    }



}
