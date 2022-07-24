package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.Content;
import com.ssafy.hoydia.domain.MatchingNote;
import com.ssafy.hoydia.domain.Page;
import com.ssafy.hoydia.domain.Title;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.repository.PageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class PageService {

    private final PageRepository pageRepository;

    @Transactional
    public String regist (Page page) {

       pageRepository.regist(page);

       return "Loc-Service : "+page.getId();
    }

    public Page searchById (String id){

        Page page = pageRepository.findById(id);

        return page;
    }

    public List<Page> searchByDiaryId(String diary_id) {

        List<Page> pageList = pageRepository.findByDiary(diary_id);

        return pageList;
    }

    @Transactional
    public String update (String id, Title title, Content content, String bgm) {

        Page page = pageRepository.findById(id);

        if(page == null) {
            throw new InvalidApproachException("존재하지 않는 페이지 입니다.");
        }
        else{

            page.setTitle(title);
            page.setContent(content);
            page.setBgmPath(bgm);

            return "Loc-Service : "+page.getId();
        }

    }

    @Transactional
    public void delete (String id) {

        pageRepository.delete(id);

    }

}
