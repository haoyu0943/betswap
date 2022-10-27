package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sys_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String userId;
    @Column(unique=true)
    private String account;
    private String password;
    private String userName;            //用户姓名
    private String userPhone;           //用户手机
    private String phoneRegionNumber;  //手机区号
    private String phoneRegion;        //手机所属地区
    private String privilege;
    private String userUnit;            //用户单位
    private String userSex;             //用户性别

    private Long   createTime;         //创建时间
    private Long   updateTime;         //最近修改时间
    private String      createUserName;           //创建人姓名
    private String      createUserId;             //创建人用户编号
    private Integer     delFlg  ;  //0正常使用1已经停用
    private String      typeFlg  ;  //0非客服人员，1商城客服，2电影客服,3支付客服
    private String      rongyuToken  ;  //在是客服的情况下，需要同步融云的token

}



