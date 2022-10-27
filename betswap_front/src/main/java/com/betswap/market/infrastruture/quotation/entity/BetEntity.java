package com.betswap.market.infrastruture.quotation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

//投注记录表
@Entity
@Table(name = "tbl_bet_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetEntity {

    @Id
    @Column(unique = true)
    private String id;//投注记录id

    private String userId;  //用户id

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String matchId; //比赛id

    private Integer quotationType; // 开盘类型(全场，让分，大小)  QuotationTypeEnum

    private String quotationId;//盘口id

    private BigDecimal betAmount;//投注金额

    private BigDecimal profitAmount; //盈利金额 押错后，写入 负的投注金额

    private BigDecimal  fee; //手续费用

    private Integer  status  ; //订单状态 OrderStatusEnum

    private Long createTime;//创建时间

    private Long updateTime;//修改时间

    private String remark; //备注

    private String blockHash; //区块哈希 也就是 交易hash  txid

    private String  transactionHash;//交易哈希

    private int  couNum; //查询次数 根据txid  查询交易记录的次数

    private String transferRemark; //备注-交易失败时可填入

    private String  txId;//web 端 转账支付的交易id

}
