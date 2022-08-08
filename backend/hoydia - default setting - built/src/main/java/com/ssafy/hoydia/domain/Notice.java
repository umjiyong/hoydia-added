package com.ssafy.hoydia.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Column (name = "notice_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 다른 Entity와는 다르게 id type을 Long으로 했으므로 주의!

    @JoinColumn(name = "user_id")
    @ManyToOne (fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private User user;

    private LocalDateTime regTime;

    private String title;

    private String content;




    @Builder
    public Notice (Long id, User user, String title, String content) {

        this.id = id;
        this.user = user;
        this.regTime = LocalDateTime.now();
        this.title = title;
        this.content = content;

    }


}
