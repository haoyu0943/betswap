package com.betswap.market.client.quotation.qry;

import com.betswap.market.client.common.vo.qry.CommonPageQry;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

//查询盈利情况
@EqualsAndHashCode(callSuper=false)
@Data
public class ProfitQuery extends CommonPageQry {

    @NotNull(message = "verification_0006")
    @ApiParam(required = true)
    private Integer transactionType; // 交易类型(1 投注 2 开盘)  QuotationTypeEnum

    @NotNull(message = "verification_0006")
    @ApiParam(required = true)
    private Integer quotationType; // 开盘类型(全场，让分，大小)  QuotationTypeEnum

    @ApiParam(value = "查询时间开始", required = false)
    private Long   startTime;//开始时间
    @ApiParam(value = "查询时间结束", required = false)
    private Long   endTime;//结束时间

}
