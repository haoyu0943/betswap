package com.betswap.market.infrastruture.quotation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

//比赛地址表
@Entity
@Table(name = "tbl_match_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchAddressEntity {

    @Id
    @Column(unique = true)
    private String id;

    private String  matchId; //比赛id

    private String  homeTeam; //主队名称

    private String  homeTeam_zht; //主队名称繁体

    private String  homeTeam_en; //主队名称英文

    private String  guestTeam;//客队名称

    private String  guestTeam_zht; //主队名称繁体

    private String  guestTeam_en; //主队名称英文

    private Long matchTime;//比赛时间

    private String  walletAddress  ;//钱包地址  （注册用户时调用对方接口写入该地址）

    private String  walkey; //私钥

    private Long createTime;//创建时间

    private Long endTime;//比赛结束时间

    private Integer  status  ; //归集状态 YesOrNoStatusEnum  (0 未归集   1 已归集)

    private Long finishTime;//归集完成时间

    private Integer intoGASStatus; // 0 未转入TRX  1 ,已转入trx

    private BigDecimal intoAmount; // 转入金额

}
