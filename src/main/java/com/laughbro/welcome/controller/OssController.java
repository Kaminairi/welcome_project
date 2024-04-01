package com.laughbro.welcome.controller;

import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.service.OssService;
import com.laughbro.welcome.utils.TimeUtils;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.OssUpLoadParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@RestController
@RequestMapping()
public class OssController {
    @Autowired
    private OssService ossService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TimeUtils timeUtils;

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


    @PostMapping("uploadfile/base64")
    public Result ossUpload_base64(@RequestBody OssUpLoadParams ossUpLoadParams) throws IOException {

        String upload_path="testbase64/";
        String base64Content = null;
        //解码
        String[] data = ossUpLoadParams.getBase64().split(",");
        base64Content = data[1];
        base64Content = base64Content.replace(" ", "+").replace("\r", "").replace("\n", "").trim();
        if (base64Content.length() >= 2) {
            base64Content = base64Content.substring(0, base64Content.length() - 2);
        }
        // 解码成字节数组
        byte[] fileBytes = Base64.getDecoder().decode(base64Content);
        MultipartFile multipartFile = new MockMultipartFile("file", ossUpLoadParams.getFilename()+".jpg", "image/jpeg", fileBytes);
        return Result.success(ossService.uploadfile(multipartFile,upload_path));
    }



    @PostMapping("uploadfile/taskbase64")
    public Result ossUpload_taskbase64(@RequestBody OssUpLoadParams ossUpLoadParams) throws IOException {

        String upload_path="tasks/uploadpictasks/"+ossUpLoadParams.getTaskid().toString()+"/";
        String base64Content = null;
        //解码
        String[] data = ossUpLoadParams.getBase64().split(",");
        base64Content = data[1];
        base64Content = base64Content.replace(" ", "+").replace("\r", "").replace("\n", "").trim();
        if (base64Content.length() >= 2) {
            base64Content = base64Content.substring(0, base64Content.length() - 2);
        }
        // 解码成字节数组
        byte[] fileBytes = Base64.getDecoder().decode(base64Content);
        String filename=ossUpLoadParams.getUserid()+"_"+timeUtils.timeGetNow()+".jpg";
        MultipartFile multipartFile = new MockMultipartFile("file", filename, "image/jpeg", fileBytes);
        //上传
        ossService.uploadfile(multipartFile,upload_path);
        //判断上传是否成功
        if(ossService.isFileUploaded(upload_path+filename)){
            //进行改表,上传记录
            if(taskMapper.insert_taskpic(ossUpLoadParams.getUserid(),ossUpLoadParams.getTaskid(),upload_path+filename)==1){
                return Result.success(null);
            }else {
                return Result.fail(201,"更新上传记录失败",null);
            }
            //
        }else {
            return Result.fail(201,"上传失败",null);
        }

    }





}
