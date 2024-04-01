package com.laughbro.welcome.vo.params.oss_params;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "oss")
public class Ossparam {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String roleArn;

    private String bucketName;

    private String regionId;

    private String version;


}

