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

//平台转账记录
@Entity
@Table(name = "tbl_platform_transactions_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatformTransactionsRecordEntity {

    @Id
    @Column(unique = true)
    private String id;//订单id

    private String leagueMatchId;//联赛id

    private String matchId;//联赛id

    private String  walkey; //平台私钥

    private String toAddress; //转出地址

    private BigDecimal amount; //交易金额

    private Integer  status  ; //交易状态 YesOrNoStatusEnum  (0 未交易 1 已交易)

    private Long createTime;//创建时间

    private String blockHash; //区块哈希

    private String  transactionHash;//交易哈希

    private String  remark; // 备注

    private String tableName; //表名

    private String majorKey; // 对应主键

    private int  couNum; //查询次数 根据txid  查询交易记录的次数

    private String transferRemark; //备注-交易失败时可填入

}
