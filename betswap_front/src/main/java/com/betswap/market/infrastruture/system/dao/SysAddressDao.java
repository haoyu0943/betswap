package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SysAddressDao extends JpaRepository<SysAddressEntity,Long> {

    @Query(value = "select * from sys_address where type=:type and ifdel=0 limit 1",nativeQuery = true)
    SysAddressEntity findAddressByType(@Param("type")int type);
}