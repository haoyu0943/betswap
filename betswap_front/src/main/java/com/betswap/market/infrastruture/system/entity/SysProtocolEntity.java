package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

//协议
@Entity
@Table(name = "sys_protocol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysProtocolEntity {

    @Id
    @Column(unique=true)
    private String id;
    private String  title;//描述
    private String  typeFlag;//类型0自己录的1外部的链接
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="Text", nullable=true)
    private String      content;           //文章内容

    private Long   createTime;         //创建时间
    private String      userId;             //创建人用户编号
    private Long   updateTime;         //最近修改时间
    private String      modifier;             //修改人id
    private String      ifNeedSign;         //是否需要签署1需要0不需要
}



