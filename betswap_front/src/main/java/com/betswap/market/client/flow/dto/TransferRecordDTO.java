package com.betswap.market.client.flow.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

//充值转账的dto
@Data
public class TransferRecordDTO {

    private String      transactionId; //订单id

    private String      userId; //用户id

    private String      payTypeName;    //支付名称

    private Long   payTime;//支付时间

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal amount;  //交易金额

    private String contractType;//传输网络
}
