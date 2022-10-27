package com.betswap.market.infrastruture.quotation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

//开盘信息表
@Entity
@Table(name = "tbl_quotation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationEntity {

    @Id
    @Column(unique = true)
    private String id;//盘口id

    private String userId;  //用户id

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String matchId; //比赛id

    private Integer quotationType; // 开盘类型(全场，让分，大小)  QuotationTypeEnum

    private Float givePoints;  //让分分数(以主场为主，主场让分1分 就是主场-1 ，客场让分就是 +1 主场加1)

    private Float specificSize;  //比大小玩法分数

    private String compareFlog ;//比大小玩法，1 为大于 , 0为小于

    private String  winTeamId; //获胜队伍id(全场时 设置为null 代表平局 )

    private BigDecimal odds;//获胜赔率

    private BigDecimal bond; //保证金

    private BigDecimal surplusBond; //保证金--剩余

    private BigDecimal backBond; //保证金--退回 --封盘后，将剩余保证金 及时的返回给用户

    private String    profitBet;//前台传过来的 押金金额

    private Integer    unitAmount;//接受多少注 （每注金额1USDT）

    private Long createTime;//创建时间

    private Long updateTime;//修改时间

    private Integer  status  ; //盘口状态 OrderStatusEnum

    private BigDecimal profitAmount; //盈利金额 押错后，写入 负的投注金额

    private BigDecimal  fee; //手续费用

    private String blockHash; //区块哈希

    private String  transactionHash;//交易哈希

    private int  couNum; //查询次数 根据txid  查询交易记录的次数

    private String transferRemark; //备注-交易失败时可填入

    private String remark; //备注

    private String  txId;//web 端 转账支付的交易id

}
