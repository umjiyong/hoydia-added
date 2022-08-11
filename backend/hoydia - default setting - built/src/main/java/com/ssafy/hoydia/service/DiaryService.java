package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.Diary;
import com.ssafy.hoydia.domain.Font;
import com.ssafy.hoydia.domain.User;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) /* Import 종류에 주의
                                   default를 readOnly = true로 해 놓고 data 수정이 필요한 service만 따로 transactional 설정
                                   참조 - https://blog.naver.com/wlsdyd1178/222717584216 */

public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Transactional
    public String regist (Diary diary) {

       diaryRepository.regist(diary);

       return "Loc-Service : "+diary.getId();
    }


    public List<Diary> searchAllDiaryOrderByRegTime(){        // 관리자용 서비스 - 모든 일기 불러오기 (시간순)
        return diaryRepository.findAllDiaryOrderByRegTime();
    }

    public Diary searchById (String id){

        Diary diary = diaryRepository.findById(id);

        return diary;
    }

    public List<Diary> searchByUserId(String userId) {

        List<Diary> diaryList = diaryRepository.findByUser(userId);

        return diaryList;
    }




    @Transactional
    public void update(String id, String title, String diaryColor, String buttonColor,String font, String fontColor, Integer fontSize , Integer drawn)  {

        Diary diary = diaryRepository.findById(id);

        if( diary == null ){
            throw new InvalidApproachException("존재하지 않는 일기입니다.");
        }
        else {

            diary.setTitle(title);
            diary.setDiaryColor(diaryColor);
            diary.setButtonColor(buttonColor);
            diary.setFont(font);
            diary.setFontColor(fontColor);
            diary.setFontSize(fontSize);
            diary.setDrawn(drawn);

        }
    }


    @Transactional
    public void delete(String id) {

        diaryRepository.delete(id);
    }

}
