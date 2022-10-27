package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SysWalletDao extends JpaRepository<SysWalletEntity,Long> {

}