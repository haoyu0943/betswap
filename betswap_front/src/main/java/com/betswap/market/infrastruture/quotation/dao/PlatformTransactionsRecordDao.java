package com.betswap.market.infrastruture.quotation.dao;

import com.betswap.market.infrastruture.quotation.entity.PlatformTransactionsRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformTransactionsRecordDao extends JpaRepository<PlatformTransactionsRecordEntity,String> {

    PlatformTransactionsRecordEntity findFirstById(String id);

}
