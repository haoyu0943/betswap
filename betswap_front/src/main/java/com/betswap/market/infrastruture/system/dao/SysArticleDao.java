package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.SysArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysArticleDao extends JpaRepository<SysArticleEntity,Long> {
    SysArticleEntity findSysArticleEntityById(String id);

}