package com.betswap.market.client.quotation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

//比赛dto
@Data
@Builder
public class WinMatchDTO {

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String  matchSeason;//赛季

    private List<TeamDTO> teams;//队伍

    private String deadline; //截止时间

    private Integer  winType;//冠军类型  1，冠军 2 垫底

}
