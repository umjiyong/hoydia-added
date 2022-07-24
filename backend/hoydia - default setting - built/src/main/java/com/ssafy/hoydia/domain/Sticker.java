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

public class Sticker {

    @Id
    @Column (name = "sticker_id")
    @Setter (AccessLevel.NONE)
    private String id;

    @JoinColumn(name = "page_id")
    @ManyToOne(fetch= FetchType.LAZY)
    @Setter (AccessLevel.NONE)
    private Page page;

    private String type;
    private String posX;
    private String posY;

    private LocalDateTime regTime;

    private String path;

    public static Sticker createSticker(Page page, String type, String posX, String posY, String path){ // 암호화 방식 미수정 상태;

        Sticker sticker = new Sticker();

        SHA256 sha256 = new SHA256();

        try {
            sticker.id = sha256.encrypt(type+path);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        sticker.page   = page;
        sticker.type   = type;
        sticker.posX   = posX;
        sticker.posY   = posY;
        sticker.regTime = LocalDateTime.now();
        sticker.path   = path;

        return sticker;
    }

}
