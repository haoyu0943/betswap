package com.betswap.market.app.user.impl;

import com.betswap.market.app.user.service.SmsService;
import com.betswap.market.app.user.service.VerifyService;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.utils.helper.mail.MailTokenHelper;
import com.betswap.market.infrastruture.utils.helper.sms.SMSTokenHelper;
import com.betswap.market.infrastruture.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.betswap.market.infrastruture.utils.redis.RedisConstants.*;


@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService {
    @Autowired
    private SmsService smsService;
    @Autowired
    private SMSTokenHelper smsTokenHelper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MailTokenHelper mailTokenHelper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ServerResponse checkEmail(String userEmail) {
        String answer = redisUtil.getAsString(MAIL_CHECK + userEmail);
        if(answer != null){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.MAIL_SUBMIT);
        }
        String emailServiceCode = mailTokenHelper.createNumber(4);
        redisUtil.set(MAIL_CHECK + userEmail,emailServiceCode,MAIL_EXPIRED_TIME);
        redisUtil.set(MAIL_CHECK_STATUS + userEmail,false,MAIL_EXPIRED_TIME);
        return emailService.sendEmail(userEmail,emailServiceCode);
    }

    @Override
    public ServerResponse verifyEmail(String userEmail, String mailContent) {
        String answer = redisUtil.getAsString(MAIL_CHECK + userEmail);

        if(answer == null){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.MAIL_EXPIRED);
        }
        else if(!answer.equals(mailContent)){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.MAIL_ERROR);
        }
        redisUtil.set(MAIL_CHECK_STATUS + userEmail,true,MAIL_CHECK_EXPIRED_TIME);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).message("验证成功");
    }

    @Override
    public ResponseEnum checkEmailStatus(String userEmail) {
        Boolean status = (Boolean) redisUtil.get(MAIL_CHECK_STATUS + userEmail);

        if(status == null){
            return ResponseEnum.MAIL_EXPIRED;
        }
        else if(!status){
            return ResponseEnum.MAIL_ERROR;
        }
        else{
            return ResponseEnum.SUCCESS;
        }
    }

    @Override
    public ResponseEnum checkPhoneCode(String userPhone, String phoneRegionNumber, String verificationCode) {
        String answer = redisUtil.getAsString(SMS_CHECK+phoneRegionNumber + userPhone);

        if(answer == null){
            return ResponseEnum.SMS_EXPIRED;
        }else if(!answer.equals(verificationCode)){
            return ResponseEnum.SMS_ERROR;
        }
        return ResponseEnum.SUCCESS;
    }


    @Override
    public ServerResponse checkPhone(String userPhone,String phoneRegionNumber ) {
        String answer = redisUtil.getAsString(SMS_CHECK+phoneRegionNumber + userPhone);
        if(answer != null){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.SMS_SUBMIT);
        }
        String createText = smsTokenHelper.createNumber(4);
        redisUtil.set(SMS_CHECK+phoneRegionNumber + userPhone,createText,SMS_EXPIRED_TIME);
//        redisUtil.set(SMS_CHECK_STATUS+phoneRegionNumber + userPhone,false,SMS_EXPIRED_TIME);
        return smsService.sendSMS(phoneRegionNumber+userPhone,createText);
    }



    @Override
    public ServerResponse verifyPhone(String userPhone,String phoneRegionNumber, String messageContent) {
        String answer = redisUtil.getAsString(SMS_CHECK+phoneRegionNumber + userPhone);

        if(answer == null){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.SMS_EXPIRED);
        }
        else if(!answer.equals(messageContent)){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.SMS_ERROR);
        }
        redisUtil.set(SMS_CHECK_STATUS+phoneRegionNumber + userPhone,true,SMS_CHECK_EXPIRED_TIME);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }

    @Override
    public ResponseEnum checkPhoneStatus(String userPhone, String phoneRegionNumber) {
        Boolean status = (Boolean) redisUtil.get(SMS_CHECK_STATUS +phoneRegionNumber+ userPhone);

        if(status == null){
            return ResponseEnum.SMS_EXPIRED;
        }
        else if(!status){
            return ResponseEnum.SMS_ERROR;
        }
        else{
            return ResponseEnum.SUCCESS;
        }
    }


}
