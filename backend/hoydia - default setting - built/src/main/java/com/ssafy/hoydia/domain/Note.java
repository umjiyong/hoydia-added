package com.ssafy.hoydia.domain;

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

public class Note {

    @Column (name = "note_id")
    @Id
    @Setter (AccessLevel.NONE)
    private String id;

    @JoinColumn(name = "user_id")
    @ManyToOne (fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private User user;

    private String question;

    private String answer;

    private LocalDateTime regTime;

    @Builder
    public Note (
            User user,
            String question,
            String answer
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
        this.question = question;
        this.answer = answer;
    }


}
