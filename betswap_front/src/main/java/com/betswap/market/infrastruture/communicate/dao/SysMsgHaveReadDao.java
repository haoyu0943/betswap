package com.betswap.market.infrastruture.communicate.dao;

import com.betswap.market.infrastruture.communicate.entity.SysMsgHaveReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface SysMsgHaveReadDao extends JpaRepository<SysMsgHaveReadEntity,Long> {

    @Query(value = "select state from sys_msg_read where user_id=:userId limit 1" ,nativeQuery = true)
    Byte[] getReadState(@Param("userId")String userId);

    @Transactional
    @Modifying
    @Query(value = "insert into sys_msg_read (user_id,state) VALUES (:userId,:state)",nativeQuery = true)
    void create(@Param("userId")String userId,@Param("state")Byte[] state);

    @Transactional
    @Modifying
    @Query(value = "update sys_msg_read set state=:state where user_id=:userId limit 1" ,nativeQuery = true)
    void updateReadState(@Param("userId")String userId,@Param("state")Byte[] state);

    @Query(value = "select COUNT(*) from sys_msg_read where user_id=:userId" ,nativeQuery = true)
    int countByUserId(@Param("userId")String userId);

}
