package com.ssafy.hoydia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.hoydia.util.SHA256;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter

public class User {

    @Column (name = "user_id")
    @Id
    @Setter (AccessLevel.NONE)
    private String id;

    private String nickname;

    private Integer birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<MatchingNote> matchingNotes = new ArrayList<>();

    public static User createUser( String nickname, Gender gender, Integer birth){ // 암호화 방식 미수정 상태;

        User user = new User();

        SHA256 sha256 = new SHA256();

        try {
            user.id = sha256.encrypt(nickname);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.nickname = nickname;
        user.birth = birth;
        user.gender = gender;

        return user;
    }

}
