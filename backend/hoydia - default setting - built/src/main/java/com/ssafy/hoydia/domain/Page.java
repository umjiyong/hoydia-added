package com.ssafy.hoydia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.hoydia.util.SHA256;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter

public class Page {

    @Column(name= "page_id")
    @Id
    @Setter (AccessLevel.NONE)
    private String id;

    @JoinColumn(name = "diary_id")
    @ManyToOne(fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private Diary diary;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    private String bgmPath;
    private String location;

    @OneToMany(mappedBy = "page")
    @JsonIgnore
    private List<Sticker> stickers = new ArrayList<>();

    public static Page createPage(Diary diary, Title title, Content content, String bgmPath, String location){

        Page page = new Page();

        SHA256 sha256 = new SHA256();

        try {
            page.id = sha256.encrypt(diary.getId()+ LocalDateTime.now());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        page.diary = diary;
        page.title = title;
        page.content = content;
        page.bgmPath = bgmPath;
        page.location = location;

        return page;
    }

}
