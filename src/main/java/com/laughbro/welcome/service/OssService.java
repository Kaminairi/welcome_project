package com.laughbro.welcome.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface OssService {
    /**
     * 获取上传文件的token
     *
     * @return
     */
    Object token();


    Map<String, String> getUrlBatch(Collection<String> filePathList);


    String uploadfile(MultipartFile file, String upload_path) throws IOException;

    int deletefile(String path);

    boolean isFileUploaded(String filePath);
}
