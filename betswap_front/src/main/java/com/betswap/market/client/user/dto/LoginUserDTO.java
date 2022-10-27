package com.betswap.market.client.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDTO {
    private String userToken;
    private String userId;
    private String userName;
    private String userPhone;
    private String phoneRegionNumber;  //手机区号
    private String phoneRegion;        //手机所属地区
    private String userEmail;
    private String privilege;
    private String userAvatar;

}
