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
import java.sql.Timestamp;

//盘口封盘退还保证金记录
@Entity
@Table(name = "tbl_quotation_back_bond_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationBackBondEntity {

    @Id
    @Column(unique = true)
    private String id;//订单id

    private String quotationId;//盘口id

    private BigDecimal surplusBond; //保证金--应退

    private BigDecimal backBond; //保证金--退回 --封盘后，将剩余保证金 及时的返回给用户--实退

    private Integer  status  ; //退还状态 YesOrNoStatusEnum  (0 退还异常 1 已退还)

    private Long createTime;//创建时间

    private String blockHash; //区块哈希

    private String  transactionHash;//交易哈希


}
