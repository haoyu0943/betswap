package com.betswap.market.client.flow.vo.query;

import com.betswap.market.client.common.vo.qry.CommonPageQry;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

//流水记录的查询
@EqualsAndHashCode(callSuper=false)
@Data
public class MyRevenueCenterQuery extends CommonPageQry {

    @NotNull(message = "日期类型不能为空")
    @ApiParam(value = "0 昨日，1本周，2本月，3 上月 ，4 本年", required = false)
    private int   type;//收益类型

}
