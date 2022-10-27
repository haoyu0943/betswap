package com.betswap.market.infrastruture.flow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


//BET 生成记录表  自己生产的 BET
@Entity
@Table(name = "produce_bet_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProduceBETRecordEntity {
    @Id
    @Column(unique = true)
    private String      id;

    private String      userId; //用户id （被释放人）

    private String      orderId; //订单id

    private Integer     type;   //订单类型OrderTypeEnum

    private BigDecimal  betNumber;  // bet数量

    private Long   createTime; //创建时间

}
