package com.betswap.market.app.user.impl;


import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@Slf4j

@ConfigurationProperties(prefix = "config.token")
public class IdCardVerifyService {

    private String secretId;
    private String secretKey;

    public ServerResponse checkIdCard(){
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("SecretId", "SecretKey");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("faceid.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            FaceidClient client = new FaceidClient(cred, "ap-beijing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            IdCardVerificationRequest req = new IdCardVerificationRequest();
            req.setIdCard("440711199612275719");
            req.setName("梁梓豪");
            // 返回的resp是一个IdCardVerificationResponse的实例，与请求对象对应
            IdCardVerificationResponse resp = client.IdCardVerification(req);
            // 输出json格式的字符串回包
            System.out.println(IdCardVerificationResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INTERNAL_ERROR);
    }
}
