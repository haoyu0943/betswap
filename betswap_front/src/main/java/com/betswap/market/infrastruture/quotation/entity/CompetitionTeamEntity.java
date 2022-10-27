package com.betswap.market.infrastruture.quotation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

//比赛队伍表
@Entity
@Table(name = "tbl_competition_team")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionTeamEntity {

    @Id
    @Column(unique = true)
    private String id;//队伍id

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String  name; //名称

    private String  name_zht; //繁体

    private String  name_en; //英文

    private String  introduce; //介绍

    private String  cover;//封面

    private Long createTime;//创建时间

    private Long updateTime;//修改时间

}
