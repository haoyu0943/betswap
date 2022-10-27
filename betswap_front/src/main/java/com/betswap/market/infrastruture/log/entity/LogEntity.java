package com.betswap.market.infrastruture.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  url;         //请求url
    private String  ip;         //访问ip
    private String  methodType;    //请求类型
    @Column(columnDefinition = "varchar(500) DEFAULT NULL")
    private String parameter;       //参数
    private Long createTime;   //创建时间

}
