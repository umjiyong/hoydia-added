package com.ssafy.hoydia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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


}
