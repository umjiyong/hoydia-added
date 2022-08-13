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

public class MatchingNote {

    @Column(name= "matching_id")
    @Id
    @Setter (AccessLevel.NONE)
    private String id;

    @JoinColumn (name = "user_id")
    @ManyToOne (fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private User user;

    private LocalDateTime regTime;

    private String ownerId;
    private String pairId;

    private String ownerQuestion;
    private String pairQuestion;

    private String ownerAnswer;
    private String pairAnswer;

    private int ownerPermit;
    private int pairPermit;

    @Builder
    public MatchingNote (
            User user,
            String ownerId,
            String pairId,

            String ownerQuestion,
            String pairQuestion,

            String ownerAnswer,
            String pairAnswer
    )
    {

        SHA256 sha256 = new SHA256();

        try {
            this.id = sha256.encrypt(user.getId()+ LocalDateTime.now());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        this.user = user;
        this.regTime = LocalDateTime.now();
        this.ownerId = ownerId;
        this.pairId = pairId;
        this.ownerQuestion = ownerQuestion;
        this.pairQuestion = pairQuestion;
        this.ownerAnswer = ownerAnswer;
        this.pairAnswer = pairAnswer;
        this.ownerPermit = 0;
        this.pairPermit = 0;

    }

}
