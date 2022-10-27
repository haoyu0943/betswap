package com.betswap.market.infrastruture.quotation.dao;

import com.betswap.market.infrastruture.quotation.entity.MatchAddressEntity;
import com.betswap.market.infrastruture.quotation.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchAddressDao extends JpaRepository<MatchAddressEntity,String> {

    MatchAddressEntity findFirstById(String id);

    MatchAddressEntity findFirstByMatchId(String matchId);
}
