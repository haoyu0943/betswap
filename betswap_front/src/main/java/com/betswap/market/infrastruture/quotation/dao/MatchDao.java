package com.betswap.market.infrastruture.quotation.dao;

import com.betswap.market.infrastruture.quotation.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDao extends JpaRepository<MatchEntity,String> {

    MatchEntity findFirstById(String id);

    @Query(value = "SELECT count(1) FROM `tbl_match` t where  t.status !=8 and if_close=0  and  t.match_time>=  :startTime and   t.match_time<  :entTime ",nativeQuery = true)
    int findMatchCountByTime(@Param("startTime")long startTime, @Param("entTime")long entTime);

    //赛果数量
    @Query(value = "SELECT count(1) FROM `tbl_match` t where   t.status =8   and  t.match_time>=  :startTime and   t.match_time<  :entTime ",nativeQuery = true)
    int findMatchReasultCountByTime(@Param("startTime")long startTime, @Param("entTime")long entTime);

    @Query(value = "SELECT count(1) FROM `tbl_match` t where  t.league_match=:league_match and t.status !=8 and  t.match_time>=  :startTime and   t.match_time<=  :entTime ",nativeQuery = true)
    int findMatchCountByTimeAndLS(@Param("startTime")long startTime, @Param("entTime")long entTime, @Param("league_match")int leagueMatch);

}
