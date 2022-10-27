package com.betswap.market.client.communicate.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MyMsgDTO {
    private Long id;
    private String userId;  //消息接收人
    private String initiator;//消息发起人(消息列表根据人员查询名称和图片)
    private String initiatorName;//消息发起人姓名
    private String initiatorCover;//消息发起人图片
    private String title;
    private String content;
    private Integer type; //账户消息 ，资产消息
    private Integer readStatus;//读取状态 0未读，1，已读
    private Long createTime;         //创建时间
}
