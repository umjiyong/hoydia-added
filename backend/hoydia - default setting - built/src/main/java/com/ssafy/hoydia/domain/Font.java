package com.ssafy.hoydia.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable

public class Font {

    private String size;

    private Style style;

    private String color;

}