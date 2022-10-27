package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sys_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysLogEntity {

    @Id
    @Column(unique=true)
    private String id;
    private String      keyword;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="Text", nullable=true)
    private String      content;           //操作备注
    private String      optTable;
    private String      optKeyId;
    private String      optIp;
    private Long   optTime;            //最近修改时间
    private String      optUserName;        //创建人姓名
    private String      optUserId;          //创建人用户编号
    private Integer     delFlg  ;           //0正常使用1已经停用
}



