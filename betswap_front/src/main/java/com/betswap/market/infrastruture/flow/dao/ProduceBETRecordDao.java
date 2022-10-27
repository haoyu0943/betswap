package com.betswap.market.infrastruture.flow.dao;

import com.betswap.market.infrastruture.flow.entity.ProduceBETRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProduceBETRecordDao extends JpaRepository<ProduceBETRecordEntity,Integer> {
}