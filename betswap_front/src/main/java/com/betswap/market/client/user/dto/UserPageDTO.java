package com.betswap.market.client.user.dto;

import lombok.Data;

@Data
public class UserPageDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String phoneRegionNumber;  //手机区号
    private String phoneRegion;        //手机所属地区
    private Integer privilege;      //用户特权级
    private String  ryToken; //融云token
}
