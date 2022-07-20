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
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<MatchingNote> matchingNotes = new ArrayList<>();

    public static User createUser(String id, String nickname, Gender gender, Integer birth){

        User user = new User();

        user.id = id;
        user.nickname = nickname;
        user.birth = birth;
        user.gender = gender;

        return user;
    }

}
