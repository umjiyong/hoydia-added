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

    public List<Page> searchByDiaryId(String diaryId) {

        List<Page> pageList = pageRepository.findByDiary(diaryId);

        return pageList;
    }

    @Transactional
    public String update (String id,
                          String title,
                          String titleFont,
                          String titleFontStyle,
                          String titleFontSize,
                          String content,
                          String contentFont,
                          String contentFontStyle,
                          String contentFontSize,
                          String bgmPath,
                          String location) {

        Page page = pageRepository.findById(id);

        if(page == null) {
            throw new InvalidApproachException("존재하지 않는 페이지 입니다.");
        }
        else{


            page.setTitle(title);
            page.setTitleFont(titleFont);
            page.setTitleFontStyle(titleFontStyle);
            page.setTitleFontSize(titleFontSize);
            page.setContent(content);
            page.setContentFont(contentFont);
            page.setContentFontStyle(contentFontStyle);
            page.setContentFontStyle(contentFontSize);
            page.setBgmPath(bgmPath);
            page.setLocation(location);

            return "Loc-Service : "+page.getId();
        }

    }

    @Transactional
    public void delete (String id) {

        pageRepository.delete(id);

    }

}
