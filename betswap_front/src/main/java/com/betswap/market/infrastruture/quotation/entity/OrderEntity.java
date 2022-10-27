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

//订单表
@Entity
@Table(name = "tbl_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @Column(unique = true)
    private String id;//订单id

    private String userId;  //用户id

    private Integer  leagueMatch;//联赛id  LeagueMatchEnum

    private String matchId; //比赛id

    private String quotationId;//盘口id

    private Integer quotationType; // 开盘类型(全场，让分，大小)  QuotationTypeEnum

    private BigDecimal betAmount;//投注金额

    private Integer  status  ; //订单状态 OrderStatusEnum

    private Long createTime;//创建时间

    private Long updateTime;//修改时间

    private String remark; //备注

}
