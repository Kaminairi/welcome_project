package com.laughbro.welcome.service.imp;


import com.aliyun.credentials.provider.RamRoleArnCredentialProvider;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.laughbro.welcome.service.OssService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.Ossparam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
