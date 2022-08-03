package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.dto.FileDto;
import com.ssafy.hoydia.service.FileService;
import com.ssafy.hoydia.util.SHA256;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Slf4j
@Api(value = "FileController", description = ("파일 컨트롤러"))
public class FileController {

    private final FileService fileService;

    @PostMapping
    public String regist (@RequestParam("file")MultipartFile files) {
        try {
            String originalFileName = files.getOriginalFilename();

            SHA256 sha256 = new SHA256();

            String[] extension = originalFileName.split("\\.");

            String customFileName = sha256.encrypt(originalFileName)+"."+extension[extension.length-1];

            String savePath = System.getProperty("user.dir") + "\\files";

            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }

            String filePath = savePath + "\\" + customFileName;
            files.transferTo(new File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOriginal(originalFileName);
            fileDto.setCustom(customFileName);
            fileDto.setPath(filePath);

            fileService.regist(fileDto);

            return filePath;
        }
        catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "fail";
    }


}
