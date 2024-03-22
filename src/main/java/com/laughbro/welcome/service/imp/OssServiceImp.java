package com.laughbro.welcome.service.imp;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;

import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.laughbro.welcome.service.OssService;

import com.laughbro.welcome.vo.params.Ossparam;

import org.python.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Service
public class OssServiceImp implements OssService {
    /**
     * 自定义的配置参数的类
     */
    @Autowired
    private Ossparam ossParam;
    @Override
    public AssumeRoleResponse token() {
        //随便写 参考下列格式即可 不要有特殊符号
        String roleSessionName = "session_1001";
        //执行角色授权
        IClientProfile profile = DefaultProfile.getProfile(
                //cn-hangzhou 或者 cn-beijing 或者 cn-shanghai 这几个节点都可以 其他的好像不行
                ossParam.getRegionId(),
                //"cn-hangzhou",
                //开通账号时下发的两个参数
                ossParam.getAccessKeyId(),
                //"LTAI5tFuMRc51PYZvnj4Vzow",
                ossParam.getAccessKeySecret()
                //"XaAAW6LOah61A0HELAo31XD6EqQ3ed"
        );
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        //version 这里是固定的 2015-04-01
        request.setVersion(ossParam.getVersion());
        //开通角色时 角色信息里的 arn 属性
        request.setRoleArn(ossParam.getRoleArn());
        request.setRoleSessionName(roleSessionName);
        //临时授权有效时间,从 900 到 3600 秒 测试的时候建议写时间给长一点，免得报错都不知道为啥
        request.setDurationSeconds(900L);
        try {
            final AssumeRoleResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 文件临时访问 URL 获取
     *
     * @param filePathList 后端存储的文件路径
     *                     例如 : web/img/abc.jpg
     *                     其实就是你存储桶里面的文件夹路径
     *                     注意最前面不要带斜杠 例如这样 /web/img/abc.jpg 不要问我为什么 自己去试试就知道了
     * @return 文件夹链接的 key : value 集合 调用方可以直接通过 map.get(filePath) 直接获取对应的链接
     */
    @Override
    public Map<String, String> getUrlBatch(Collection<String> filePathList) {
        //这里 等于 Map<String, String> resultMap = new HashMap<>(); 只是我引用了工具包看起来舒服一点
        Map<String, String> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(filePathList)) {
            return resultMap;
        }
        //这个token可以用redis存起来过期了再重新生成，效率会高一点
        //过期时间保持一致 或者redis缓存时间稍微短一点，错开那种刚好过期的时间点就好了
        //比如 token 900秒（15分钟）到期，你就设置redis的key 720秒（12分钟）到期，完美错开
        AssumeRoleResponse token = token();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(
                ossParam.getEndpoint(),
                token.getCredentials().getAccessKeyId(),
                token.getCredentials().getAccessKeySecret(),
                token.getCredentials().getSecurityToken()
        );
        //过期时间尽量短一点 1-3 分钟即可，
        //只要前端能够显示出来就行了 （时间太长，可能会被人恶意访问造成流量泄露，产生大量费用。切记！！！！）
        //以前小白的时候直接生成10年有效的链接。。。现在看起来是真的虎
        Long expirationTime = System.currentTimeMillis()+100000L ;
        Date expirationDate = new Date(expirationTime);
        filePathList.forEach(filePath -> {
            //生成GET请求链接
            URL url = ossClient.generatePresignedUrl(ossParam.getBucketName(), filePath, expirationDate);
            resultMap.put(filePath, url.toString());
        });
        return resultMap;
    }

    @Override
    public String uploadfile(MultipartFile file, String upload_path) throws IOException {
        InputStream inputStream = file.getInputStream();
        // 重命名文件,避免文件覆盖
        //String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) +"-"+ multipartFile.getOriginalFilename();
        //return upload(inputStream, fileName);
        // 上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(ossParam.getEndpoint(), ossParam.getAccessKeyId(), ossParam.getAccessKeySecret());
        ossClient.putObject(ossParam.getBucketName(),upload_path+file.getOriginalFilename(),inputStream);
        // 访问路径: https://[bucketName].[endpointHost]/fileName
        // 例如: https://zifeiyu01-oss.oss-cn-fuzhou.aliyuncs.com/20240220120000-1.jpg
        //String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        ossClient.shutdown();
        return upload_path;
    }


}
