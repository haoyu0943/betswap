package com.betswap.market.client.quotation.qry;

import com.betswap.market.infrastruture.common.request.PageConfig;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class MatchAllQuery {
    @NotNull(message = "verification_0043")
    @Min(value = 0,     message = "verification_0044")
    Integer pageNum = PageConfig.pageNum;

    @NotNull(message = "verification_0045")
    @Min(value = 1,     message = "verification_0046")
    @Max(value = 100,   message = "verification_0047")
    Integer pageSize = PageConfig.pageSize;


    @NotNull(message = "message_3002")
    @ApiParam(required = true)
    private Integer orderType; //排序规则 （0 热门 1 按时间 ）

    private Integer  leagueMatch;//联赛类型  LeagueMatchEnum

    private Integer ifHot; // （1 热门 ）


    @ApiParam(value = "查询时间开始", required = false)
    private Long   startTime;//比赛开始时间
    @ApiParam(value = "查询时间结束", required = false)
    private Long   endTime;//比赛结束时间

//    private Integer matchTimeType;//比赛时间类型 matchTimeEnum

    private String keyword;   //关键词--主队+客队名称



}
