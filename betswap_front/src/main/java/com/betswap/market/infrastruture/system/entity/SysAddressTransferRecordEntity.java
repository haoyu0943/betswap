package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


//平台账户转账记录
@Entity
@Table(name = "sys_address_transfer_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysAddressTransferRecordEntity {

    @Id
    @Column(unique = true)
    private String id;

    private String sysAddressId;// 系统钱包地址id

    private String  type; //转账类型 1 USDT 2 TRX

    private String  inOrOut; //转账类型 1 转出 2 转入

    private String  fromAddress  ;//平台钱包地址

    private String  toAddress  ;//比赛钱包地址

    private BigDecimal amount  ;//钱包地址  （注册用户时调用对方接口写入该地址）

    private Long createTime;//创建时间

    private String  remark  ;//备注

}



