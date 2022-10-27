package com.betswap.market.infrastruture.utils.helper.sms;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "sms")
@Data
public class SMSHelper {
    private String secretId;
    private String secretKey;
    private String SmsSdkAppid;
    private String TemplateID;
    private String Signature;

    public String sendSMS(String number,String content){
        try{

            Credential cred = new Credential(secretId, secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {number};
            req.setPhoneNumberSet(phoneNumberSet1);

            String[] templateParamSet1 = {content};
            req.setTemplateParamSet(templateParamSet1);

            req.setSmsSdkAppid(SmsSdkAppid);
            req.setTemplateID(TemplateID);
            req.setSign(Signature);

            SendSmsResponse resp = client.SendSms(req);

            System.out.println(SendSmsResponse.toJsonString(resp));
            return resp.getSendStatusSet()[0].getCode();

        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
    }

}
