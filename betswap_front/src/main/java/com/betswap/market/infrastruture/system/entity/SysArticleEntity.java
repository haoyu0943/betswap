package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sys_article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysArticleEntity {

    @Id
    @Column(unique=true)
    private String id;
    private String  title;//标题
    private String  subtitle;//标题
    private String  cover;//新闻题图
    private String  typeFlag;//类型0自己录的1外部的链接
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="Text", nullable=true)
    private String      content;           //文章内容
    private String      webUrl;            //外部链接，typeFlag=‘1’的时候有值

    private Long   createTime;         //创建时间
    private Long   updateTime;         //最近修改时间
    private String      userName;           //创建人姓名
    private String      userId;             //创建人用户编号
    private String      userPhone;          //创建人手机
}



