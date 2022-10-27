package com.betswap.market.client.user.dto;

import lombok.Data;

@Data
public class OtherUserInfoDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String phoneRegionNumber;  //手机区号
    private String phoneRegion;        //手机所属地区
    private String userAvatar;
    private String userIntroduction;
    private String  ryToken; //融云token
}
