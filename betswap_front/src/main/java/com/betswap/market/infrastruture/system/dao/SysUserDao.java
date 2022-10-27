package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SysUserDao extends JpaRepository<SysUserEntity,Long> {

    boolean existsByUserId(String userId);
    boolean existsByUserName(String userName);
    SysUserEntity findSysUserEntityByUserId(String userId);
    SysUserEntity findSysUserEntityByUserName(String userName);


    @Query(value = "select user_name from sys_user where user_id = :user_id",nativeQuery = true)
    String findUserNameByUserId(@Param("user_id") String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sys_user set user.password = :password where user.user_id = :user_id",nativeQuery = true)
    int updatePassword(@Param("user_id") String userId, @Param("password") String password);


    @Query(value = "select privilege from sys_user where user_id = :user_id",nativeQuery = true)
    Integer findPrivilegeByUserId(@Param("user_id") String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sys_user set user.privilege = :privilege where user.user_id = :user_id",nativeQuery = true)
    int updatePrivilege(@Param("user_id") String userId, @Param("privilege") String privilege);

    @Query(value = "select * from sys_user where del_flg=0 and type_flg = :type_flg ORDER BY RAND() limit 1",nativeQuery = true)
    SysUserEntity findByTypeFlg(@Param("type_flg")String typeFlg);
}