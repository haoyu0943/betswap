package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "sys_role_bet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleBETEntity {

    @Id
    @Column(unique = true)
    private String id;
    private String      agentRank;             //代理等级
    private String      agentRankName;         //代理等级
    private Integer     thresholdIncome;       //要达到的收益数
    private Integer     rewardsRatio;          //奖励比例,列的都是百分数
    private Integer     subordinateCount;      //直推好友数量
    private Integer     dailyReleaseRatio;     //日释放比例
    private Integer      dataType;             //1商城2影视

    private Long   createTime;         //创建时间
    private Long   updateTime;         //最近修改时间
    private String      userName;           //创建人姓名
    private String      userId;             //创建人用户编号
    private String      userPhone;          //创建人手机

    private Integer beginDays;  //开始释放天数(多少天后开始释放)
}



