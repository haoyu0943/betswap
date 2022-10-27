package com.betswap.market.client.flow.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class FlowCommonDTO {

    private String      userId; //用户id

    private String      orderNumber;    //订单号

    private String  revenueType;//收益类型

    private int payType;  //支付类型 0法币 1.USDT 2.BET

//    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal oldAmount;  //原始金额

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal amount;  //交易金额----今日收益

    private String amountDw;  //交易金额----带单位

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal nowAmount;  //交易后金额

    private Long   createTime;//创建时间

    private String   remark;// 备注 因为什么获取收益 ，必填

    private String name;//名称
}
