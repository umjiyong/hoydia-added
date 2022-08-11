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

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    private String bgmPath;
    private String location;

    @OneToMany(mappedBy = "page")
    @JsonIgnore
    private List<Sticker> stickers = new ArrayList<>();

    @Builder
    public Page (
            Diary diary,
            Title title,
            Content content,
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
        this.content = content;
        this.bgmPath = bgmPath;
        this.location = location;

    }


}
