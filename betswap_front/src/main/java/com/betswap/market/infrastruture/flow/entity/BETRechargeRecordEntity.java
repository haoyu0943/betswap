package com.betswap.market.infrastruture.flow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


//BET充值记录
@Entity
@Table(name = "bet_recharge_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BETRechargeRecordEntity {
    @Id
    @Column(unique = true)
    private String id;
    private String      userId; //操作人

    private BigDecimal oldAmount;  //原始金额

    private BigDecimal amount;  //交易金额

    private BigDecimal nowAmount;  //交易后金额

    private Long   createTime;//创建时间

    private String remark;   //备注
}
