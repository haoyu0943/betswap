package com.betswap.market.client.quotation.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

//比赛dto
@Data
@Builder
public class MatchDTO {

    private String id;//比赛id

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String  homeTeamId; //主队id

    private String  homeTeam; //主队名称中文

    private String  homeTeam_zht; //主队名称繁体

    private String  homeTeam_en; //主队名称英文

    private String  guestTeamId;//客队id

    private String  guestTeam;//客队名称

    private String  guestTeam_zht; //主队名称繁体

    private String  guestTeam_en; //主队名称英文

    private Long matchTime;//比赛时间

    private Integer  homeScore; //主场得分

    private Integer  guestScore; //客场得分

    private Long endTime;//比赛结束时间

    private String    status; //比赛状态  MatchStatusEnum

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal homeOdds;//主队获胜赔率

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal tieOdds;//平局获胜赔率

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal guestOdds;//客队获胜赔率

    private String  homeCover;//主队封面

    private String  guestCover;//客队封面

    //让分类型
    private Float homeGivePoints;  //让分分数(以主场为主，主场让分1分 就是主场-1 ，客场让分就是 +1 主场加1)
    private Float guestGivePoints;  //让分分数(以主场为主，主场让分1分 就是主场-1 ，客场让分就是 +1 主场加1)
    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal rfHomeOdds;//让分主场获胜赔率

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal rfGuestOdds;//让分客场获胜赔率


    // 比大小玩法
    private String homeSpecificSize;  //比大小玩法分数 大于
    private String guestSpecificSize;  //比大小玩法分数 大于

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal dxHomeOdds;//比大小主场获胜赔率

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal dxGuestOdds;//比大小客场获胜赔率

    private Integer  ifClose;//是否封盘 根据系统配置时间 后台定时执行(0 否 1 是)

    private Integer  ifBetAndQ;//是否可以开盘 比赛时间在10天后 (0 否 1 是)

}
