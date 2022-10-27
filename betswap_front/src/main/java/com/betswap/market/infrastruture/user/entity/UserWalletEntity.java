package com.betswap.market.infrastruture.user.entity;

import com.betswap.market.infrastruture.utils.Md5.DesUtil;
import lombok.Builder;

import javax.persistence.*;
import java.math.BigDecimal;

//用户钱包
@Entity
@Table(name = "user_wallet")
@Builder
public class UserWalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String userId;

    @Builder.Default
    private BigDecimal usdt = new BigDecimal(0);   //USDT

    @Builder.Default
    private BigDecimal ownBet = new BigDecimal(0);   //可用的Bet

    private String ownBetKey;//法币加密字段
    public UserWalletEntity(){

    }

    public UserWalletEntity(Long id, String userId, BigDecimal usdt, BigDecimal ownBet, String ownBetKey) {
        this.id = id;
        this.userId = userId;
        this.usdt = usdt;
        this.ownBet = ownBet;
        this.ownBetKey = ownBetKey;
    }


    public BigDecimal getOwnBet() throws Exception {
        if(DesUtil.decryptWal(ownBetKey).compareTo(ownBet)!=0){
            throw new Exception();
        }
        return ownBet;
    }

    public void setOwnBet(BigDecimal ownBet) throws Exception {
        this.ownBet = ownBet;
        this.ownBetKey=DesUtil.encryptWal(ownBet);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getUsdt() {
        return usdt;
    }

    public void setUsdt(BigDecimal usdt) {
        this.usdt = usdt;
    }

    public String getOwnBetKey() {
        return ownBetKey;
    }

    public void setOwnBetKey(String ownBetKey) {
        this.ownBetKey = ownBetKey;
    }
}
