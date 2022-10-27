package com.betswap.market.client.quotation.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

//投注支付参数
@Data
public class BetPayWebCmd {

//    @NotBlank(message = "verification_0006")
//    @ApiParam(required = true)
//    private String quotationId; //盘口id--

    @NotNull(message = "verification_0006")
    @ApiParam(required = true)
    private BigDecimal betAmount; // 投注金额

    @NotNull(message = "verification_0006")
    @ApiParam(required = true)
    private String orderId; // 订单id

    @NotNull(message = "verification_0006")
    @ApiParam(required = true)
    private String txId; // 交易id--转账成功后获取的值

}
