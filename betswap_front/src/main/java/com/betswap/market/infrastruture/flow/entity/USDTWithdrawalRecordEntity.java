package com.betswap.market.infrastruture.flow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


//USDT转账记录
@Entity
@Table(name = "ustd_withdrawal_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class USDTWithdrawalRecordEntity {
    @Id
    @Column(unique = true)
    private String id;
    private String      userId; //操作人

    private String  outAddress ;//转出地址

    private String  inAddress ;//接收地址

    private BigDecimal oldAmount;  //原始金额

    private BigDecimal amount;  //交易金额

    private BigDecimal nowAmount;  //交易后金额

    private Long   createTime;//创建时间

    private String remark;   //备注

}
