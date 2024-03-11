package com.laughbro.welcome.service;


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
}
