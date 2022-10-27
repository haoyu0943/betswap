package com.betswap.market.infrastruture.user.dao;

import com.betswap.market.infrastruture.user.entity.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWalletDao extends JpaRepository<UserWalletEntity,Integer> {

    UserWalletEntity findByUserId(String userId);

}
