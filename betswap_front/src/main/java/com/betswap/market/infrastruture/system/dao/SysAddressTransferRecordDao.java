package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysAddressEntity;
import com.betswap.market.infrastruture.system.entity.SysAddressTransferRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SysAddressTransferRecordDao extends JpaRepository<SysAddressTransferRecordEntity,Long> {

}