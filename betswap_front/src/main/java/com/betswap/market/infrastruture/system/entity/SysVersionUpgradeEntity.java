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

//版本控制
@Entity
@Table(name = "sys_version_upgrade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysVersionUpgradeEntity {

    @Id
    @Column(unique=true)
    private String id;

    private int androidEdition; //版本

    private String androidIdentification; //版本标识

    private String androidDownloadUrl; //安卓下载地址

//    private int iosEdition; //版本

    private String iosIdentification; //版本标识

    private String   msgTitle;  //提示标题

    private String   msgContent;  //提示标题

    private Timestamp createTime; //创建时间

}



