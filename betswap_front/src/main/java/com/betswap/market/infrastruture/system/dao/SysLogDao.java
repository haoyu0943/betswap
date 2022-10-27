package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogDao extends JpaRepository<SysLogEntity,Long> {

}