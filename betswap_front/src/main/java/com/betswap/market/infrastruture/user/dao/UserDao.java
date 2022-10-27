package com.betswap.market.infrastruture.user.dao;

import com.betswap.market.infrastruture.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao extends JpaRepository<UserEntity,Long> {

    boolean existsByUserId(String userId);
    boolean existsByUserPhoneAndPhoneRegionNumber(String userPhone,String phoneRegionNumber);
    boolean existsByUserName(String userName);

    UserEntity findByUserId(String userId);
    UserEntity findByUserName(String userName);
    UserEntity findByUserPhoneAndPhoneRegionNumber(String userPhone,String phoneRegionNumber);

    UserEntity findFirstByInvitationCode(String code);
    UserEntity findFirstByWalletAddress(String address);

    @Query(value = "select user_name from user where user_id = :user_id",nativeQuery = true)
    String findUserNameByUserId(@Param("user_id") String userId);

    @Query(value = "select wallet_address from user where user_id = :user_id",nativeQuery = true)
    String findUserWalletAddressByUserId(@Param("user_id") String userId);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update user set user.user_name = :user_name where user.user_id = :user_id",nativeQuery = true)
    int updateUserName(@Param("user_id") String userId, @Param("user_name") String user_name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update user set user.user_avatar = :user_avatar where user.user_id = :user_id",nativeQuery = true)
    int updateUserAvatar(@Param("user_id") String userId, @Param("user_avatar") String user_avatar);

    @Query(value = "select privilege from user where user_id = :user_id",nativeQuery = true)
    Integer findPrivilegeByUserId(@Param("user_id") String userId);

    @Query(value = "select invitation_code from user where user_id = :user_id",nativeQuery = true)
    String findInvittionCodeByUserId(@Param("user_id") String userId);

    @Query(value = "select wallet_address from user where user_id = :user_id",nativeQuery = true)
    String findUseWalletAddressByUserId(@Param("user_id")String userId);



}