package com.betswap.market.infrastruture.flow.dao;

import com.betswap.market.infrastruture.flow.entity.BETWithdrawalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BETWithdrawalRecordDao extends JpaRepository<BETWithdrawalRecordEntity,Integer> {

}