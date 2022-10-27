package com.betswap.market.client.flow.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FlowRecordDTO {

    private String      id;    //订单号

    private String      userId; //用户id

    private String  type; //类型

    private int payType;  //支付类型 0法币 1.USDT 2.BET

    private String buyType; //购买类型 1.盲盒，2.基金 3.影城 4.商城

    private String amount;  //交易金额----今日收益

    private Long   createTime;//创建时间

    private String   orderId;//创建时间
}
