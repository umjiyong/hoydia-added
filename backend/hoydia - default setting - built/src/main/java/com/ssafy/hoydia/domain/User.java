package com.ssafy.hoydia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.hoydia.util.SHA256;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor

public class User {

    @Column (name = "user_id")
    @Id
    @Setter (AccessLevel.NONE)
    @NotBlank
    private String id;

    private String nickname;

    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String gender;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<MatchingNote> matchingNotes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Notice> notices = new ArrayList<>();

//    public static User createUser(String email, Platform platform, Role role){
//
//        User user = new User();
//
//        SHA256 sha256 = new SHA256();
//
//        try {
//            user.id = sha256.encrypt(email+ LocalDateTime.now());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        user.email = email;
//        user.platform = platform;
//        user.role = role;
//
//        return user;
//    } // id 암호화 추후 처리

    @Builder
    public User (String id,
                 String nickname,
                 String email,
                 Platform platform,
                 Role role,
                 String gender
                 )
    {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.platform = platform;
        this.role =role;
        this.gender = gender;
    }


}
