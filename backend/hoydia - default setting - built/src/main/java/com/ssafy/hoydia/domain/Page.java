package com.ssafy.hoydia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.hoydia.util.SHA256;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Page {

    @Column(name= "page_id")
    @Id
    @Setter (AccessLevel.NONE)
    @NotBlank
    private String id;

    @JoinColumn(name = "diary_id")
    @ManyToOne(fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private Diary diary;

    @Setter (AccessLevel.NONE)
    private LocalDateTime regTime;

    private String title;

    private String titleFontStyle;

    private String titleFontSize;

    private String content;

    private String contentFontStyle;

    private String contentFontSize;

    private String bgmPath;

    private String location;

    @OneToMany(mappedBy = "page")
    @JsonIgnore
    private List<Sticker> stickers = new ArrayList<>();

    @Builder
    public Page (

            Diary diary,
            String title,
            String titleFontStyle,
            String titleFontSize,
            String content,
            String contentFontStyle,
            String contentFontSize,
            String bgmPath,
            String location
    )
    {

        SHA256 sha256 = new SHA256();

        try {
            this.id = sha256.encrypt(diary.getId()+ LocalDateTime.now());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.diary = diary;
        this.regTime = LocalDateTime.now();
        this.title = title;
        this.titleFontStyle = titleFontStyle;
        this.titleFontSize = titleFontSize;
        this.content = content;
        this.contentFontStyle = contentFontStyle;
        this.contentFontSize = contentFontSize;
        this.bgmPath = bgmPath;
        this.location = location;

    }


}
