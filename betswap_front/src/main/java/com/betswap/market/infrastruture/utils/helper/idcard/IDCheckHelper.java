package com.betswap.market.infrastruture.utils.helper.idcard;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.CheckPhoneAndNameRequest;
import com.tencentcloudapi.faceid.v20180301.models.CheckPhoneAndNameResponse;
import com.tencentcloudapi.faceid.v20180301.models.PhoneVerificationRequest;
import com.tencentcloudapi.faceid.v20180301.models.PhoneVerificationResponse;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sms")
@Data
public class IDCheckHelper {
    private String secretId;
    private String secretKey;

    public Boolean CheckPhoneAndName(String phone,String name){
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(secretId, secretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("faceid.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            FaceidClient client = new FaceidClient(cred, "ap-beijing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CheckPhoneAndNameRequest req = new CheckPhoneAndNameRequest();
            req.setMobile(phone);
            req.setName(name);
            // 返回的resp是一个CheckPhoneAndNameResponse的实例，与请求对象对应
            CheckPhoneAndNameResponse resp = client.CheckPhoneAndName(req);
            if(resp.getResult().equals("0")){
                return true;
            }else {
                return false;
            }
//            // 输出json格式的字符串回包
//            System.out.println(CheckPhoneAndNameResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean CheckPhoneAndNameAndIDCard(String phone,String name,String idCard){
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(secretId, secretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("faceid.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            FaceidClient client = new FaceidClient(cred, "ap-beijing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            PhoneVerificationRequest req = new PhoneVerificationRequest();
            req.setIdCard(idCard);
            req.setName(name);
            req.setPhone(phone);
            // 返回的resp是一个PhoneVerificationResponse的实例，与请求对象对应
            PhoneVerificationResponse resp = client.PhoneVerification(req);
            if(resp.getResult().equals("0")){
                return true;
            }else {
                return false;
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return false;
    }
}
