package com.betswap.market.infrastruture.user.entity;

import com.betswap.market.client.user.enums.UserPrivilegeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String userId;
//    @Column(unique=true)
    private String userName;
    
    private String password;

    private String moneyPassword;

//    @Column(unique=true)
    private String userPhone;           //用户手机

    private String phoneRegionNumber;  //手机区号

    private String phoneRegion;        //手机所属地区

    private String userAvatar;          //用户封面

    @Builder.Default
    private Integer privilege = UserPrivilegeEnum.NORMAL.status;     //用户特权级

    private Integer rank;   //会员等级

    private Integer     onlineDays;             //上线天数
    private Long   latestOnlineTime;     //最新上线时间

    private Long createTime;     //创建时间

    private Long updateTime;     //修改时间

    private BigDecimal totalBETRevenue;  //BET总收益（后台定时跑写入）
    private BigDecimal totalUSDTRevenue;  //USDT总收益（后台定时跑写入）

    private String invitationCode;//邀请码

    private String  walletAddress  ;//钱包地址  （注册用户时调用对方接口写入该地址）
    private String  walkey; //私钥

    private BigDecimal  totalBet; //获得的总的bet
    private BigDecimal  toReleasedBet;//待释放的bet--待释放 是通过 总数和级别（现在的级别）和在线天数 如果有的话 就释放 剩余不足释放数，就有多少释放多少。


    private Long latestReleasedTime;    //bet最新释放时间

    private String  ryToken; //融云token

    private String customerServiceId;//客服人员账号
    private String customerServiceName;//客户人员姓名

    private String  countryName; //国家名称


    private String  mnemonicWords;//助记词

    private String  publicKey;//公钥

    private String passwordPrompt ;//密码提示
}



