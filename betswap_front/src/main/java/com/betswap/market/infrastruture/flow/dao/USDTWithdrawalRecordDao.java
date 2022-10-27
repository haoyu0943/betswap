package com.betswap.market.infrastruture.flow.dao;

import com.betswap.market.infrastruture.flow.entity.USDTWithdrawalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface USDTWithdrawalRecordDao extends JpaRepository<USDTWithdrawalRecordEntity,Integer> {

}