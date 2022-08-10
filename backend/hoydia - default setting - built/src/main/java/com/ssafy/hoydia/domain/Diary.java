package com.ssafy.hoydia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.hoydia.util.SHA256;
import lombok.*;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Diary {

    @Column(name= "diary_id")
    @Id
    @Setter (AccessLevel.NONE)
    private String id;

    @JoinColumn (name = "user_id")
    @ManyToOne (fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private User user;

    @Setter (AccessLevel.NONE)
    private LocalDateTime regTime;

    private String ownerId;
    private String pairId;

    private boolean own;

    private String title;

    private String diaryColor;

    private String buttonColor;

    private Integer drawn;

    @OneToMany(mappedBy = "diary")
    @JsonIgnore
    private List<Page> pages = new ArrayList<>();

    @Builder
    public Diary (
            User user,
            String ownerId,
            String pairId,
            String title,
            String diaryColor,
            String buttonColor
    )
    {
        SHA256 sha256 = new SHA256();

        try {
            this.id = sha256.encrypt(ownerId+pairId+LocalDateTime.now());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.user = user;
        this.regTime = LocalDateTime.now();
        this.ownerId = ownerId;
        this.pairId = pairId;
        this.own = true;
        this.title = title;
        this.diaryColor = diaryColor;
        this.buttonColor = buttonColor;
        this.drawn = 0;
    }

}
