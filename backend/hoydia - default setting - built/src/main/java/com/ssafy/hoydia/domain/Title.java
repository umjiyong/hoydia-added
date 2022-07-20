package com.ssafy.hoydia.domain;

import javax.persistence.*;

@Embeddable

public class Title {


    @Column(name = "title")
    private String text;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name= "size",column = @Column(name = "title_font_size")),
            @AttributeOverride(name="style",column = @Column(name = "title_font_style")),
            @AttributeOverride(name="color",column = @Column(name = "title_font_color"))})
    private Font font;





}
