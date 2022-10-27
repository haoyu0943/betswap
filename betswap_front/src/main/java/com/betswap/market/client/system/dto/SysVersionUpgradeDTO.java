package com.betswap.market.client.system.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SysVersionUpgradeDTO {

    private int androidEdition; //安卓版本

    private String androidIdentification; //安卓版本标识

    private String androidDownloadUrl; //安卓下载地址

//    private int iosEdition; //苹果版本

    private String iosIdentification; //苹果版本标识

    private String   msgTitle;  //提示标题

    private String   msgContent;  //提示内容

    private Timestamp createTime; //创建时间

}
