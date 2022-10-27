package com.betswap.market.infrastruture.user.entity;

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
@Table(name = "advertisement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertisementEntity {
    @Id
    @Column(unique = true)
    private String      id;            //编号
    private String      coverImage;    //广告图片
    private String      title;         //广告的标题

    private Long   createTime;         //创建时间
    private Long   updateTime;         //最近修改时间
    private String      userName;           //创建人姓名
    private String      userId;             //创建人用户编号
    private String      userPhone;          //创建人手机
    private Integer     delFlg;             //状态 0正常1已经删除
    private Integer     topFlg;             //是否置顶
    private Integer     typeFlg;            //1商城的2电影的
    private String      relId;              //关联的商品编号或者电影编号
    private String      linkUrl;            //链接的地址

    private Long   timeOnline;          //上架时间
    private Long   timeOffline;         //下架时间


}

