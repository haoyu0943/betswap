package com.betswap.market.app.user.impl;

import com.betswap.market.app.user.service.SmsService;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.user.dao.UserDao;
import com.betswap.market.infrastruture.utils.helper.sms.SMSHelper;
import com.betswap.market.infrastruture.utils.helper.sms.SMSTokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SMSTokenHelper smsTokenHelper;
    @Autowired
    private SMSHelper smsHelper;
    @Autowired
    private UserDao userDao;

    @Override
    public ServerResponse sendSMS(String userPhone, String messageContent){
        try {
            String result = smsHelper.sendSMS(userPhone,messageContent);
            if(result.equals("Ok"))
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
            else
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PHONE_ERROR).data(result);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    public ServerResponse sendSMSRegister(@RequestParam("number") String number, HttpServletResponse httpServletResponse){
        try {
            String createText = smsTokenHelper.createNumber(4);
            String token = smsTokenHelper.createToken(createText);
            httpServletResponse.setHeader("Verify-Token", token);
            String result = smsHelper.sendSMS("+86"+number,createText);
            if(result.equals("Ok"))
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(token);
            else
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED).data(result);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }


}
