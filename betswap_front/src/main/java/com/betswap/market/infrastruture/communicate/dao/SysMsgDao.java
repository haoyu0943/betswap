package com.betswap.market.infrastruture.communicate.dao;

import com.betswap.market.infrastruture.communicate.entity.SysMsgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysMsgDao extends JpaRepository<SysMsgEntity,Long> {

    @Query(value = "select * from sys_msg order by create_time DESC limit :limitStart,10" ,nativeQuery = true)
    List<SysMsgEntity> queryAllSysMsg(@Param("limitStart")int limitStart);

    @Query(value = "select COUNT(*) from sys_msg" ,nativeQuery = true)
    int countSysMsg();

    @Query(value = "select * from sys_msg where id=:Id limit 1" ,nativeQuery = true)
    SysMsgEntity findSysMsgById(@Param("Id")long Id);

    SysMsgEntity findSysMsgEntityByMessageId(String messageid);

}
