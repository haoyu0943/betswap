package com.betswap.market.client.quotation.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class QuotationCmd {

    private String id;//盘口id

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String matchId; //比赛id

    private Integer quotationType; // 开盘类型(全场，让分，大小)  QuotationTypeEnum

    private Float givePoints;  //让分分数(以主场为主，主场让分1分 就是主场-1 ，客场让分就是 +1 主场加1)

    private Float specificSize;  //比大小玩法分数

    private String compareFlog ;//比大小玩法，1 为大于 , 0为小于

    private String  winTeamId; //获胜队伍id(全场时 设置为null 代表平局 ) 当类型 为 大小时 大于 是 传 主队 ，小于时传客队

    private BigDecimal odds;//获胜赔率

    @NotNull(message = "verification_0045")
    @Min(value = 1,     message = "verification_0046")
    private Integer unitAmount; //接受多少注 （每注金额1USDT）

    private String    profitBet;//前台传过来的 押金金额



}
