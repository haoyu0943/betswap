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

//比赛取消返回开盘和投注的金额
@Entity
@Table(name = "tbl_match_cancel_back_bond_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchCancelBackBondEntity {

    @Id
    @Column(unique = true)
    private String id;//订单id

    private String majorId;//主键id

    private Integer type;//主键类型 1 投注 2 开盘

    private BigDecimal amount; //投注金额 和开盘金额

    private Integer  status  ; //退还状态 YesOrNoStatusEnum  (0 退还异常 1 已退还)

    private Long createTime;//创建时间

    private String blockHash; //区块哈希

    private String  transactionHash;//交易哈希

    private String  remark;  // 备注


}
