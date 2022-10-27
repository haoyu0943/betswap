package com.betswap.market.client.user.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class UserInfoDTO{

    private String userId;
    private String userName;
    private String userPhone;           //用户手机
    private String phoneRegionNumber;  //手机区号
    private String phoneRegion;        //手机所属地区
    private String userAvatar;          //用户封面


    private String rank;   //团队等级

    private String rankStatus;   //团队等级描述

    private String onlineDays;  //上线天数

    private Long latestOnlineTime;     //最新上线时间

    private Integer privilege;     //用户特权级 0.各项功能正常 1.禁止登陆 2.掌柜 3.管理员

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal totalBETRevenue;  //BET总收益

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal totalUSDTRevenue;  //USDT总收益


    private String invitationCode;//邀请码

    private String walletAddress;//钱包地址

    private String parentInvitationCode;//父级邀请码


    private String  ryToken; //融云token

    private UserWalletDTO walletDTO;//钱包信息

    private  Integer unReadCount; //未读状态

    private String  countryName; //国家名称

    private  Integer  ifMnemonic;  //0 没有 1 有


}
