package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.Sticker;
import com.ssafy.hoydia.exception.InvalidApproachException;
import com.ssafy.hoydia.repository.StickerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class StickerService {

    private final StickerRepository stickerRepository;

    @Transactional
    public String regist (Sticker sticker) {

       stickerRepository.regist(sticker);

       return "Loc-Service : "+sticker.getId();
    }

    public Sticker searchById (String id){

        Sticker sticker = stickerRepository.findById(id);

        return sticker;
    }

    public List<Sticker> searchByPageId(String page_id) {

        List<Sticker> stickerList = stickerRepository.findByPage(page_id);

        return stickerList;
    }


    @Transactional
    public String update (String id, String posX, String posY, String type) {

        Sticker sticker = stickerRepository.findById(id);

        if(sticker == null) {
            throw new InvalidApproachException("잘못된 접근입니다.");
        }
        else{

            sticker.setRegTime(LocalDateTime.now());        // update시에 항상 갱신.
            sticker.setPosX(posX);
            sticker.setPosY(posY);
            sticker.setType(type);

            return "Loc-Service : "+sticker.getId();
        }

    }


    @Transactional
    public void delete (String id) {

        stickerRepository.delete(id);

    }

}
