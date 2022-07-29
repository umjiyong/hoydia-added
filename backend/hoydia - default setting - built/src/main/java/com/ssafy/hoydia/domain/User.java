package com.ssafy.hoydia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.hoydia.util.SHA256;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter

public class User {

    @Column (name = "user_id")
    @Id
    @Setter (AccessLevel.NONE)
    private String id;

    private String name;

    private String nickname;

    private String birth;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

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

    public static User createUser(String name, String email, Role role){

        User user = new User();

        SHA256 sha256 = new SHA256();

        try {
            user.id = sha256.encrypt(email+ LocalDateTime.now());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.name = name;
        user.email = email;
        user.role = role;

        return user;
    }

}
