package com.betswap.market.infrastruture.log.dao;

import com.betswap.market.infrastruture.log.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LogDao extends JpaRepository<LogEntity,Long> {

}
