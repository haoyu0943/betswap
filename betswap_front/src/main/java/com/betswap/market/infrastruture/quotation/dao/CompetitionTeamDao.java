package com.betswap.market.infrastruture.quotation.dao;

import com.betswap.market.infrastruture.quotation.entity.CompetitionTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionTeamDao extends JpaRepository<CompetitionTeamEntity,String> {

    CompetitionTeamEntity findFirstById(String homeTeamId);

}
