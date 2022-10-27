package com.betswap.market.infrastruture.flow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


//收益记录流水
@Entity
@Table(name = "revenue_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevenueRecordEntity {

    @Id
    @Column(unique = true)
    private String id;

    private String  orderId;//订单id

    private String  revenueType;//收益类型 ProfitTypeEnum

    private String  payType;//支付类型 PayTypeEnum

    private String  userId;  //用户id

    private String  teamId;  // 团队id--如果有团队 ，统计团队收益记录

    @Builder.Default
    private BigDecimal amount = new BigDecimal(0);   //收益金额--

    private Long createTime;//创建时间

    private String      remark;// 备注 因为什么获取收益 ，必填


}
