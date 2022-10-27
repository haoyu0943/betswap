package com.betswap.market.client.user.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MerchantApplyRecordDTO {
    private String recordId;
    private String userId;///申请人
    private String name;  //店铺名称
    private String content;    //经营范围
    private String storeCover; //店铺封面
    private String storeIntroduction;    //店铺介绍

    private Integer     status;    //申请状态 0.待审批 1.审批通过 2.审批驳回
    private String     reviewer;///审核人
    private String     opinion;    //审核意见
    private Long  opinionTime;  //审核时间
    private Long  createTime;  //申请时间

}
