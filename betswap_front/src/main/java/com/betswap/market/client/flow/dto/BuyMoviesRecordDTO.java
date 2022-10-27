package com.betswap.market.client.flow.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BuyMoviesRecordDTO {

    private String      userId; //用户id

    private String      orderNumber;    //订单号


    private int payType;  //支付类型 0法币 1.USDT 2.BET


//    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal amount;  //交易金额----今日收益

    private String amountDw;  //交易金额----带单位

    private Long   createTime;//创建时间

    private String name;//名称
}
