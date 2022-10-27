package com.betswap.market.client.quotation.qry;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

//具体投注信息查询页面参数
@Data
public class BetQuery {

    @NotBlank(message = "verification_0006")
    @ApiParam(required = true)
    private String matchId; //比赛id

    @NotNull(message = "verification_0006")
    @ApiParam(required = true)
    private BigDecimal odds;//获胜赔率

    @NotNull(message = "verification_0006")
    @ApiParam(required = true)
    private Integer quotationType; // 开盘类型(全场，让分，大小)  QuotationTypeEnum

//    private String compareFlog ;//比大小玩法，1 为大于 , 0为小于
//    private Float specificSize;  //比大小玩法分数(显示的是左侧的让分值)
    private String specificSize;  //比大小玩法分数(显示的是左侧的让分值)

    private Float givePoints;  //让分分数(显示的是左侧的让分值)

    private String  winTeamId; //获胜队伍id(全场时 设置为null 代表平局 ) 当为 比大小时 大于传 1  小于 0

}
