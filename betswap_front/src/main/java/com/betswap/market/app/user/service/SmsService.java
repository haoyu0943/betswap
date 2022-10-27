package com.betswap.market.app.user.service;

import com.betswap.market.infrastruture.common.response.ServerResponse;

public interface SmsService {
    public ServerResponse sendSMS(String userPhone, String messageContent);
}
