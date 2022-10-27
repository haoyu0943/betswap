package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysParaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysParaDao extends JpaRepository<SysParaEntity,Long> {

    @Query(value = "select * from sys_para where id=:id limit 1",nativeQuery = true)
    SysParaEntity findSysParaEntityById(@Param("id")int Id);

    @Query(value = "select para_value from sys_para where id=:id limit 1",nativeQuery = true)
    String findParaValueById(@Param("id")int Id);

    @Query(value = "select para_value from sys_para where FIND_IN_SET (id,:ids) order by id asc ",nativeQuery = true)
    List<String> findParaValueByIdIn(@Param("ids") String ids);
}