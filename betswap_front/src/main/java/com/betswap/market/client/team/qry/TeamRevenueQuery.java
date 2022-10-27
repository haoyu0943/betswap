package com.betswap.market.client.team.qry;

import com.betswap.market.client.common.vo.qry.CommonPageQry;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//团队收益
@EqualsAndHashCode(callSuper=false)
@Data
public class TeamRevenueQuery extends CommonPageQry {

    @NotNull(message = "verification_0006")
    @ApiParam(value = "是否统计亏损", required = true)
    private Integer   ifLoss;

    @ApiParam(value = "团队id", required = false)
    private String   teamId;

    @NotBlank(message = "verification_0006")
    @ApiParam(required = true)
    private String payType; //支付类型 PayTypeEnum

    @ApiParam(value = "查询时间开始", required = false)
    private Long   startTime;//开始时间
    @ApiParam(value = "查询时间结束", required = false)
    private Long   endTime;//结束时间

}
