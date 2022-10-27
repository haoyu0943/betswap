package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysVersionUpgradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysVersionUpgradeDao extends JpaRepository<SysVersionUpgradeEntity,Long> {

    @Query(value = "select * from sys_version_upgrade order by  create_time desc limit 0,1", nativeQuery = true)
    SysVersionUpgradeEntity findLatestVersion();


}
