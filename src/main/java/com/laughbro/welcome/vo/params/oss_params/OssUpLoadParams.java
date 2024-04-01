package com.laughbro.welcome.vo.params.oss_params;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.laughbro.welcome.dao.mapper.BagMapper;
import lombok.Data;

import java.math.BigInteger;

@Data
public class OssUpLoadParams {
    private String base64;
    private String filename;
    private String type;
    private BigInteger taskid;
    private String userid;
}
