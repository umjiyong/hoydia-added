package com.ssafy.hoydia.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class File {

    @Column(name= "file_id")
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String orginalFileName;

    private String customFileName;

    private String filePath;

    @Builder
    public File (Long id, String original, String custom, String path) {

        this.id = id;
        this.orginalFileName = original;
        this.customFileName = custom;
        this.filePath = path;

    }



}
