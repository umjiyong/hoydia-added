package com.ssafy.hoydia.repository;

import com.ssafy.hoydia.domain.Page;
import com.ssafy.hoydia.domain.Sticker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class StickerRepository {

    private final EntityManager em;

    public String regist(Sticker sticker) {

        em.persist(sticker);

        return "Loc-Repository : "+sticker.getId();

    }

    public Sticker findById(String id) {

        return em.find(Sticker.class,id);

    }


    public List<Sticker> findByPage(String pageId) { // 해당 페이지의 모든 스티커를 가져옴

        List<Sticker> stickers = em.createQuery("SELECT s FROM Sticker s WHERE s.page.id = :page_id",Sticker.class)
                .setParameter("page_id",pageId)
                .getResultList();

        return stickers;

    }



    public void delete (String id) {

        em.remove(findById(id));

    }


}
