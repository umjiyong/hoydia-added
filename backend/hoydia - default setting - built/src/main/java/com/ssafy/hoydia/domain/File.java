package com.ssafy.hoydia.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class File {

    @Id
    private String id;

    private String orginalFileName;

    private String customFileName;

    private String filePath;

    @Builder
    public File (String id, String original, String custom, String path) {

        this.id = id;
        this.orginalFileName = original;
        this.customFileName = custom;
        this.filePath = path;

    }



}
