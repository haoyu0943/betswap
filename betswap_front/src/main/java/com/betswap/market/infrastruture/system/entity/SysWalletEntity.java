package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//平台钱包地址
@Entity
@Table(name = "sys_wallet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysWalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer  leagueMatch;//联赛id  LeagueMatchEnum
    private String  walletAddress  ;//钱包地址
    private String  walkey; //私钥
}



