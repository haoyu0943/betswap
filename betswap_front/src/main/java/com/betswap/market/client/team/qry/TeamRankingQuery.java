package com.betswap.market.client.team.qry;

import com.betswap.market.client.common.vo.qry.CommonPageQry;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

//团队排名 统计
@EqualsAndHashCode(callSuper=false)
@Data
public class TeamRankingQuery extends CommonPageQry {

    @ApiParam(value = "是否统计亏损", required = true)
    private Integer   ifLoss;

    @ApiParam(value = "团队id", required = true)
    private String   teamId;

}
