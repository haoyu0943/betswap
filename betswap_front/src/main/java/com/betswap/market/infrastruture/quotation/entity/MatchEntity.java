package com.betswap.market.infrastruture.quotation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

//比赛信息表
@Entity
@Table(name = "tbl_match")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEntity {

    @Id
    @Column(unique = true)
    private String id;//比赛id

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String  homeTeamId; //主队id

    private String  homeTeam; //主队名称

    private String  homeTeamCover; //主队封面

    private String  homeTeam_zht; //主队名称繁体

    private String  homeTeam_en; //主队名称英文

    private String  guestTeamId;//客队id

    private String  guestTeam;//客队名称

    private String  guestTeamCover;//客队名称

    private String  guestTeam_zht; //主队名称繁体

    private String  guestTeam_en; //主队名称英文

    private Long matchTime;//比赛时间

    private Integer  homeScore; //主场得分

    private Integer  guestScore; //客场得分

    private Long endTime;//比赛结束时间

    private Integer    status; //比赛状态  MatchStatusEnum

    private Long createTime;//创建时间

    private Long updateTime;//修改时间

    private Integer  ifHot;//是否热门

    private Integer  ifClose;//是否封盘 根据系统配置时间 后台定时执行(0 否 1 是) YesOrNoStatusEnum

}
