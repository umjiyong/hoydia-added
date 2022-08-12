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
    @NotBlank
    private Diary diary;

    @Setter (AccessLevel.NONE)
    private LocalDateTime regTime;

    private String title;

    private String titleFont;

    private String titleFontStyle;

    private String titleFontSize;

    private String content;

    private String contentFont;

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
            String titleFont,
            String titleFontStyle,
            String titleFontSize,
            String content,
            String contentFont,
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
        this.titleFont = titleFont;
        this.titleFontStyle = titleFontStyle;
        this.titleFontSize = titleFontSize;
        this.content = content;
        this.contentFont = contentFont;
        this.contentFontStyle = contentFontStyle;
        this.contentFontSize = contentFontSize;
        this.bgmPath = bgmPath;
        this.location = location;

    }


}
