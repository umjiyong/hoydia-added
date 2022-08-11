package com.ssafy.hoydia.domain;

import javax.persistence.*;

@Embeddable

public class Content {

    @Column (name = "Content")
    private String text;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name= "size",column = @Column(name = "content_font_size")),
            @AttributeOverride(name="style",column = @Column(name = "content_font_style")),
            @AttributeOverride(name="color",column = @Column(name = "content_font_color"))})

    private Font font;


}
