package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysArticleEntity;
import com.betswap.market.infrastruture.system.entity.SysIpCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SysIpCountryDao extends JpaRepository<SysIpCountryEntity,Long> {
    SysArticleEntity findSysArticleEntityById(String id);

    @Query(value = "select * from sys_ipcountry where ip_start_numb <= :ip and ip_end_numb >= :ip  limit 0,1 ",nativeQuery = true)
    SysIpCountryEntity findFirstByIpLong(@Param("ip")Long ip);
}