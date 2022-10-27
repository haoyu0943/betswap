package com.betswap.market.client.flow.vo.query;

import com.betswap.market.client.common.vo.qry.CommonPageQry;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

//流水记录的查询
@EqualsAndHashCode(callSuper=false)
@Data
public class FlowRecordAllQuery extends CommonPageQry {

    @ApiParam(value = "流水类型 0 购买,1 充值 ,2 提现，3 收益", required = false)
    private String   type;//收益类型

    @ApiParam(value = "支付类型 0法币 1.USDT 2.BET", required = false)
    private String   payType;//收益类型


    @ApiParam(value = "查询时间开始", required = false)
    private Long   startTime;//收益类型
    @ApiParam(value = "查询时间结束", required = false)
    private Long   endTime;//收益类型
}
