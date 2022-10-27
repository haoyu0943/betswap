package com.betswap.market.infrastruture.flow.entity;

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


//待释放代理收益表
@Entity
@Table(name = "to_released_agent_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToReleasedAgentRecordEntity {
    @Id
    @Column(unique = true)
    private String id;

    private String      userId; //用户id （当前用户id）

    private String      orderId; //订单id

    @Builder.Default
    private boolean     bValid=true;    //结算收益后设为false

    private BigDecimal    amount;// 收益金额

    private Long   createTime;//创建时间

    private Long   updateTime;//修改时间

}
