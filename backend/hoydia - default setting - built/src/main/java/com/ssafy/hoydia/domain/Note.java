package com.ssafy.hoydia.domain;

import com.ssafy.hoydia.util.SHA256;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
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

    public static Note createNote (User user, String question, String answer) {

        Note note = new Note();

        SHA256 sha256 = new SHA256();

        try {
            note.id = sha256.encrypt(user.getId()+LocalDateTime.now());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        note.user = user;

        note.regTime = LocalDateTime.now();

        note.question = question;

        note.answer = answer;

        return note;

    }


}
