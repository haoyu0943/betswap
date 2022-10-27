package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sys_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysAddressEntity {

    @Id
    @Column(unique = true)
    private String id;

    private String  type; //类型 1 USDT 2 TRX

    private String  walletAddress  ;//钱包地址  （注册用户时调用对方接口写入该地址）

    private String  walkey; //私钥

    private Long createTime;//创建时间

    private Long updateTime;//修改时间

    private Integer  ifdel;//是否删除 0 未删除，1 删除

}



