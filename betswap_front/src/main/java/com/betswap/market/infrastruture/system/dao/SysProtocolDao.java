package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysProtocolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SysProtocolDao extends JpaRepository<SysProtocolEntity,Long> {

    @Query(value = "select if_need_sign from sys_protocol where type_flag=:typeFlag limit 1",nativeQuery = true)
    String findParaValueByTypeFlag(@Param("typeFlag")int typeFlag);

}