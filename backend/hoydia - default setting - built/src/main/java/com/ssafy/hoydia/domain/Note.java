package com.ssafy.hoydia.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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


}
