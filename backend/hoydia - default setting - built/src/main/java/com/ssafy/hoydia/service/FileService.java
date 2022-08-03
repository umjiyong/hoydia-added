package com.ssafy.hoydia.service;

import com.ssafy.hoydia.domain.File;
import com.ssafy.hoydia.dto.FileDto;
import com.ssafy.hoydia.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public Long regist (FileDto fileDto) {

        return (fileRepository.regist(fileDto.toEntity()));

    }

    @Transactional
    public FileDto searchById (Long id) { // return이 filedto 인 것에 주의.

        File file = fileRepository.findById(id);

        FileDto fileDto = FileDto.builder()
                .id(id)
                .original(file.getOrginalFileName())
                .custom(file.getCustomFileName())
                .path(file.getFilePath())
                .build();

        return fileDto;

    }





}
