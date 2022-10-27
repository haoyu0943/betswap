package com.betswap.market.client.quotation.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

//交易记录
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetRecordDTO {

    private Integer transactionType;

    //投注信息或者盘口id
    private String id;//投注记录id

    private String userId;  //用户id

    private String walletAddress;//用户地址

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String matchId; //比赛id

    private Integer quotationType; // 开盘类型(全场，让分，大小)  QuotationTypeEnum

//    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal betAmount;//投注金额

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal profitAmount; //盈利金额

    private BigDecimal  fee; //手续费用

    private Integer  status  ; //订单状态 OrderStatusEnum

    private Long createTime;//创建时间（投注开盘公用）

    private Long updateTime;//修改时间

    private String remark; //备注

    private String blockHash; //区块哈希 （投注开盘公用）

    private String  transactionHash;//交易哈希（投注开盘公用）


    //盘口信息
    private String quotationId;//盘口id

    private String  winTeamId; //获胜队伍id(全场时 设置为null 代表平局 )

    private String  winTeamName; //获胜队伍名称

    private String winTeamName_zht; //获胜队伍名称繁体

    private String winTeamName_en; //获胜队伍名称英文

    private String  winTeamCover;//获胜队伍封面

    private BigDecimal odds;//获胜赔率

    private String givePointsLeft;  //让分分数/比大小（左侧）+1 -1 >1 <1
    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal betAmountSum;//总的投注金额

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal bond; //保证金

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal backBond; //退回保证金

    private Long  betCount;//投注人数



    //比赛信息
    private String  homeTeamId; //主队id

    private String  homeTeam; //主队名称

    private String  homeTeam_zht; //主队名称繁体

    private String  homeTeam_en; //主队名称英文

    private String  guestTeam;//客队id

    private String  guestTeam_zht; //主队名称繁体

    private String  guestTeam_en; //主队名称英文

    private String  guestTeamId;//客队名称


    private String  homeCover;//主队封面

    private String  guestCover;//客队封面

    private Long matchTime;//比赛时间

    private Integer  homeScore; //主场得分

    private Integer  guestScore; //客场得分

    private Long endTime;//比赛结束时间

    private Integer    teamStatus; //比赛状态  MatchStatusEnum



}
