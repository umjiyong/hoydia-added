package com.ssafy.hoydia.controller;

import com.ssafy.hoydia.dto.FileDto;
import com.ssafy.hoydia.service.AwsS3Service;
import com.ssafy.hoydia.service.FileService;
import com.ssafy.hoydia.util.SHA256;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Slf4j
@Api(value = "FileController", description = ("파일 컨트롤러"))
public class FileController {

    private final FileService fileService;

    private final AwsS3Service awsS3Service;

//    @PostMapping
//    @ApiOperation(value="파일 스태틱 하게 저장", notes = "죽은 기능")
//    public String regist (@RequestParam("file")MultipartFile files) {
//        try {
//            String originalFileName = files.getOriginalFilename();
//
//            SHA256 sha256 = new SHA256();
//
//            String[] extension = originalFileName.split("\\.");
//
//            String customFileName = sha256.encrypt(originalFileName+ LocalDateTime.now())+"."+extension[extension.length-1];
//
//            String savePath = System.getProperty("user.dir") + "\\files";      // 저장되는 위치
//
//            if (!new File(savePath).exists()) {
//                try{
//                    new File(savePath).mkdir();
//                }
//                catch(Exception e){
//                    e.getStackTrace();
//                }
//            }
//
//            String filePath = savePath + "\\" + customFileName;
//            files.transferTo(new File(filePath));
//
//            FileDto fileDto = new FileDto();
//            fileDto.setOriginal(originalFileName);
//            fileDto.setCustom(customFileName);
//            fileDto.setPath(filePath);
//
//            fileService.regist(fileDto);
//
//            return filePath;
//        }
//        catch (NoSuchAlgorithmException e) {
//              e.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return "fail";
//    }

    @PostMapping("/upload")
    @ApiOperation(value="파일 서버에 올리기", notes = "(parameter는 catgory와 파일 (body)) 서버에 올리는 작업이므로 서버 키가 있어야 함(백엔드 문의) + 돈 나올 수 있는 기능이니 조금만..주의")
    public String uploadFile(
            @RequestParam("category") String category,
            @RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadFileV1(category, multipartFile);
    }


}
