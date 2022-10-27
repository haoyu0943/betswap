package com.betswap.market.client.system.dto;

import lombok.Data;

@Data
public class SysUserDTO {

    private String userId;
    private String userName;            //用户姓名

    private String userPhone;           //用户手机
    private String phoneRegionNumber;  //手机区号
    private String phoneRegion;        //手机所属地区

}
