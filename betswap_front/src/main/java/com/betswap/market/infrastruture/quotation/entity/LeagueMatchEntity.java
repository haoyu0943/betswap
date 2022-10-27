package com.betswap.market.infrastruture.quotation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

//比赛信息表
@Entity
@Table(name = "tbl_league_match")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueMatchEntity {

    @Id
    @Column(unique = true)
    private String id;

    private Integer  leagueMatchId;//联赛id  LeagueMatchEnum

    private String  name; //名称

    private String  name_en; //名称-英文

    private String  name_zht; //名称-繁体

    private String  crawlingName; //爬取名称

    private String  walletAddress  ;//钱包地址  （注册用户时调用对方接口写入该地址）
    private String  walkey; //私钥

    private String  logo; //logo

    private Long updateTime;//修改时间

}
