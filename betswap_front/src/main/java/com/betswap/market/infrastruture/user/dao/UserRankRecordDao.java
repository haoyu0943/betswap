package com.betswap.market.infrastruture.user.dao;

import com.betswap.market.infrastruture.user.entity.UserRankRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRankRecordDao extends JpaRepository<UserRankRecordEntity,Integer> {
}
