package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.OssService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping()
public class OssController {
    @Autowired
    private OssService ossService;

    @GetMapping(value = "/oss/token")
    //这里的R是我自己封装的统一返回对象 你也可以直接返回JSON啥的
    public Result ossToken() {
        return Result.success(ossService.token());
    }

    @GetMapping(value = "/oss/url")
    public Result ossUrl(String filepath){
        return Result.success(ossService.getUrlBatch(Collections.singleton(filepath)));
    }

    @PostMapping("/uploadfile/avatar")
    public Result ossUpload_avatar(@RequestParam("file") MultipartFile file) throws IOException {
        String upload_path="users/faceimg/";
        return Result.success(ossService.uploadfile(file,upload_path));
    }

    @PostMapping("/uploadfile/ad")
    public Result ossUpload_ad(@RequestParam("file") MultipartFile file) throws IOException {
        String upload_path="advertisement/";
        return Result.success(ossService.uploadfile(file,upload_path));
    }

    @PostMapping("/uploadfile/task")
    public Result ossUpload_task(@RequestParam("file") MultipartFile file) throws IOException {
        String upload_path="task/";
        return Result.success(ossService.uploadfile(file,upload_path));
    }

}
