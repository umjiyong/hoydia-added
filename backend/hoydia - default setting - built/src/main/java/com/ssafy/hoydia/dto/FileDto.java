package com.ssafy.hoydia.dto;

import com.ssafy.hoydia.domain.File;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class FileDto {

    private String id;
    private String original;
    private String custom;
    private String path;

    public File toEntity (String id, String original, String custom, String path) {
        File file =File.builder()
                .id(id)
                .original(original)
                .custom(custom)
                .path(path)
                .build();
        return file;
    }

    @Builder
    public FileDto (String id, String original, String custom, String path) {
        this.id = id;
        this.original = original;
        this.custom = custom;
        this.path = path;
    }

}
