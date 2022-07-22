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

public class MatchingNote {

    @Column(name= "matching_id")
    @Id
    @Setter (AccessLevel.NONE)
    private String id;

    @JoinColumn (name = "user_id")
    @ManyToOne (fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private User user;

    private String ownerId;
    private String pairId;

    private String ownerQuestion;
    private String pairQuestion;

    private String ownerAnswer;
    private String pairAnswer;

    public static MatchingNote createMatchingNote (User user,
    String ownerId,
    String pairId,
    String ownerQuestion,
    String pairQuestion,
    String ownerAnswer,
    String pairAnswer){

        MatchingNote matchingNote = new MatchingNote();

        SHA256 sha256 = new SHA256();

        try {
            matchingNote.id = sha256.encrypt(ownerId+pairId);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        matchingNote.user = user;
        matchingNote.ownerId = ownerId;
        matchingNote.pairId = pairId;
        matchingNote.ownerQuestion = ownerQuestion;
        matchingNote.pairQuestion = pairQuestion;
        matchingNote.ownerAnswer = ownerAnswer;
        matchingNote.pairAnswer = pairAnswer;

        return matchingNote;
    }

}
