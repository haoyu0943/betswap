package com.betswap.market.app.user.service;


import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;

public interface VerifyService {
    public ServerResponse checkPhone(String userPhone,String phoneRegionNumber);
    public ServerResponse verifyPhone(String userPhone,String phoneRegionNumber, String messageContent);
    public ResponseEnum checkPhoneStatus(String userPhone, String phoneRegionNumber);
    public ServerResponse checkEmail(String userEmail);
    public ServerResponse verifyEmail(String userEmail, String mailContent);
    public ResponseEnum checkEmailStatus(String userEmail);

    ResponseEnum checkPhoneCode(String userPhone, String phoneRegionNumber, String verificationCode);
}
