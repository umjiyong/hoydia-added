package com.ssafy.hoydia.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter

public class Sticker {

    @Id
    @Column (name = "sticker_id")
    @Setter (AccessLevel.NONE)
    private String id;

    @JoinColumn(name = "page_id")
    @ManyToOne(fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private Page page;

    private String type;
    private String posX;
    private String posY;

    private String path;


}
