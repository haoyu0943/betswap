package com.betswap.market.app.user.impl;

import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.utils.helper.mail.MailHelper;
import com.betswap.market.infrastruture.utils.helper.mail.MailTokenHelper;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private MailTokenHelper mailTokenHelper;

    public ServerResponse sendEmail(String userEmail, String content){
        if(!mailHelper.sendMail(userEmail,content))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INTERNAL_ERROR);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }

    public boolean UpdatePassWordMail(String userEmail, String uuid){
        return mailHelper.UpdatePassWordMail(userEmail,uuid);
    }
}
