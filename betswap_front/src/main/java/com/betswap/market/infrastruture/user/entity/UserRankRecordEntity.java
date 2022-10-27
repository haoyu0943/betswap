package com.betswap.market.infrastruture.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

//用户等级升级记录
@Entity
@Table(name = "user_rank_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserRankRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String subordinateId;//下级 用户id
    private Integer rank;     //等级
    private Integer rankType;   //等级类型 UserRankTypeEnum
    private Long createTime;     //创建时间
    private String remark; //备注
}



