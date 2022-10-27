package com.betswap.market.infrastruture.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "Baidulog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaiduLogEntity {

    //对于调用百度api进行人脸识别，也许需要进行日志？



    @Id
    private Long Time;   //创建时间
    private String  Api;      //调用的接口
    private String  parameter;//参数
    private String  res;         //访问ip
}
