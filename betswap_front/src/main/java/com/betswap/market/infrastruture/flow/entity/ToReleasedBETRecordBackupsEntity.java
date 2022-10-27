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


//待释放BET备份表--删除的会放入此表
@Entity
@Table(name = "to_released_bet_record_backups")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToReleasedBETRecordBackupsEntity {
    @Id
    @Column(unique = true)
    private String id;

    private String      orderId; //订单id(投注 或者盘口id)

    private String      userId; //用户id （被释放人）

    private String     teamId;  //团队id

    private String  revenueType;//收益类型

    private Integer beginDays;  //开始释放天数(多少天后开始释放)

    private BigDecimal    surplusNumber;// 剩余数量

    private BigDecimal    totalNumber; //总数量 （）

    private BigDecimal    dailyNumber; //每日数量（根据生成这条数据时的总量和当时用户的释放比率求出，有小数向上取整）

    private Long   createTime;//创建时间

    private Long   updateTime;//更新时间

}
