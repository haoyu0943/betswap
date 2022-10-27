package com.betswap.market.infrastruture.quotation.dao;

import com.betswap.market.infrastruture.quotation.entity.LeagueMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueMatchDao extends JpaRepository<LeagueMatchEntity,String> {


    @Query(value = "select * from tbl_league_match where league_match_id= :leagueMatchId  limit 0,1" ,nativeQuery = true)
    LeagueMatchEntity findFirstById(@Param("leagueMatchId")String leagueMatchId);

    @Query(value = "select wallet_address from tbl_league_match where league_match_id= :leagueMatchId limit 0,1" ,nativeQuery = true)
    String findAddressById(@Param("leagueMatchId")Integer leagueMatchId);

    @Query(value = "select walkey from tbl_league_match where league_match_id= :leagueMatchId limit 0,1" ,nativeQuery = true)
    String findWalKeyById(@Param("leagueMatchId")Integer leagueMatchId);

    @Query(value = "SELECT * FROM `tbl_league_match`  where  league_match_id in (:ids) order by league_match_id asc  ",nativeQuery = true)
    List<LeagueMatchEntity> findAllByMatchIDIn(@Param("ids")List<String> ids);
}
