package com.laughbro.welcome.vo.params;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import lombok.Data;

@Data
public class OssUpLoadParams {
    private String base64;
    private String filename;
    private String type;
}
