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

    private String diaryColor;

    private Integer drawn;

    @OneToMany(mappedBy = "diary")
    @JsonIgnore
    private List<Page> pages = new ArrayList<>();

    public static Diary createDiary (User user,
                                                   LocalDateTime regTime,
                                                   String ownerId,
                                                   String pairId,
                                                   boolean own,
                                                   String diaryColor,
                                                   Integer drawn){

        Diary diary = new Diary();

        SHA256 sha256 = new SHA256();

        try {
            diary.id = sha256.encrypt(ownerId+pairId+regTime);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        diary.user = user;
        diary.regTime = regTime;
        diary.ownerId = ownerId;
        diary.pairId = pairId;
        diary.own = own;
        diary.diaryColor = diaryColor;
        diary.drawn = drawn;

        return diary;
    }

}
