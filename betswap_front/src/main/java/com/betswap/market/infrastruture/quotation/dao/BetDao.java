package com.betswap.market.infrastruture.quotation.dao;

import com.betswap.market.infrastruture.quotation.entity.BetEntity;
import com.betswap.market.infrastruture.quotation.entity.MatchEntity;
import com.betswap.market.infrastruture.quotation.entity.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BetDao extends JpaRepository<BetEntity,String> {

    BetEntity findFirstById(String id);

    @Query(value = "select IFNULL(count(1),0),IFNULL(sum(profit_amount),0) from tbl_bet_record where user_id= :user_id and  status=:status",nativeQuery = true)
    List<List<Object>> findTotalSituation(@Param("user_id")String userId, @Param("status")Integer status);

    @Query(value = "select IFNULL(count(1),0) from tbl_bet_record where user_id= :user_id and profit_amount>0" ,nativeQuery = true)
    Integer findTotalWinning(@Param("user_id")String userId);

    @Query(value = "select IFNULL(count(1),0) from tbl_bet_record where user_id= :user_id " ,nativeQuery = true)
    Integer  countByUserId(@Param("user_id")String userId);

    @Modifying
    @Query(value = "update tbl_bet_record set status=:status where match_id= :match_id " ,nativeQuery = true)
    int updateStatusByMatchId(@Param("match_id")String matchId, @Param("status")Integer status);

    Integer countTotalByUserId(String userId);

    @Query(value = "select IFNULL(count(1),0) from tbl_bet_record where user_id= :user_id and status= :status " ,nativeQuery = true)
    Integer countByUserIdAndStatus(@Param("user_id")String userId, @Param("status")Integer status);

    @Query(value = "select IFNULL(count(1),0) from tbl_bet_record where quotation_id= :quotation_id  and status in (0,1,2,4,6) " ,nativeQuery = true)
    Long  countByQuotationId(@Param("quotation_id")String quotation_id);

    @Query(value = "select IFNULL(sum(bet_amount),0) from tbl_bet_record where quotation_id= :quotation_id   and status in (0,1,2,4,6) " ,nativeQuery = true)
    BigDecimal totalBetAmountByQuotationId(@Param("quotation_id")String quotation_id);


    //全场

    @Query(value = "SELECT ta.odds FROM (select win_team_id,odds,IFNULL(sum(betAmount),0) AS betAmount,IFNULL(sum(betCount),0) AS betCount from `quotation_bet` AS t " +
            " WHERE t.status=0 and  t.match_id =:match_id AND t.quotation_type = 1 and  t.surplus_bond>0 GROUP BY win_team_id,odds) AS ta" +
            " where ta.win_team_id=:win_team_id ORDER BY ta.betAmount DESC,ta.betCount DESC LIMIT 0,1",nativeQuery = true)
    BigDecimal findFullCourtHotOddsByMatchIdAndWinTeamId(@Param("match_id") String matchId,@Param("win_team_id")String winTeamId);

    @Query(value = "SELECT ta.odds FROM (select win_team_id,odds,IFNULL(sum(betAmount),0) AS betAmount,IFNULL(sum(betCount),0) AS betCount from `quotation_bet` AS t " +
            " WHERE t.status=0 and  t.match_id =:match_id AND t.quotation_type = 1 and t.surplus_bond>0 GROUP BY win_team_id,odds) AS ta" +
            " where ta.win_team_id is Null ORDER BY ta.betAmount DESC,ta.betCount DESC LIMIT 0,1",nativeQuery = true)
    BigDecimal findFullCourtHotOddsByMatchIdAndTie(@Param("match_id") String matchId);

    //让分
    @Query(value = "SELECT  ta.odds,ta.give_points,ta.win_team_id FROM (select give_points,win_team_id,odds,IFNULL(sum(betAmount),0) AS betAmount,IFNULL(sum(betCount),0) AS betCount from `quotation_bet` AS t " +
            " WHERE t.status=0 and  t.match_id =:match_id AND t.quotation_type = 2 and t.surplus_bond>0 GROUP BY give_points,win_team_id,odds) AS ta" +
            " where ta.win_team_id =:win_team_id  ORDER BY ta.betAmount DESC,ta.betCount DESC LIMIT 0,1",nativeQuery = true)
    List<List<Object>>  findRFHotOddsByMatchId(@Param("match_id") String matchId,@Param("win_team_id")String winTeamId);

    //大小
    @Query(value = "SELECT  ta.odds,ta.specific_size,ta.win_team_id FROM (select specific_size,win_team_id,odds,IFNULL(sum(betAmount),0) AS betAmount,IFNULL(sum(betCount),0) AS betCount from `quotation_bet` AS t " +
            " WHERE t.status=0 and  t.match_id =:match_id AND t.quotation_type = 3 and t.surplus_bond>0 GROUP BY specific_size,win_team_id,odds) AS ta" +
            " where ta.win_team_id =:win_team_id  ORDER BY ta.betAmount DESC,ta.betCount DESC LIMIT 0,1",nativeQuery = true)
    List<List<Object>> findDxHotOddsByMatchId(@Param("match_id") String matchId,@Param("win_team_id")String winTeamId);


    //**************************** 所有 *******************************//TODO
    //全场

    @Query(value = "SELECT ta.odds,ta.bond,ta.surplusBond FROM (select win_team_id,odds,IFNULL(sum(betAmount),0) AS betAmount,IFNULL(sum(betCount),0) AS betCount," +
            "sum(bond) as bond,sum(surplus_bond) as surplusBond from `quotation_bet` AS t " +
            " WHERE  t.status=0 and t.match_id =:match_id AND t.quotation_type = 1 GROUP BY win_team_id,odds) AS ta" +
            " where ta.win_team_id=:win_team_id ORDER BY ta.betAmount DESC,ta.betCount DESC",nativeQuery = true)
    List<List<Object>>   findFullCourtHotOddsByMatchIdAndWinTeamIdAll(@Param("match_id") String matchId,@Param("win_team_id")String winTeamId);

    @Query(value = "SELECT ta.odds,ta.bond,ta.surplusBond FROM (select win_team_id,odds,IFNULL(sum(betAmount),0) AS betAmount,IFNULL(sum(betCount),0) AS betCount," +
            "sum(bond) as bond,sum(surplus_bond) as surplusBond  from `quotation_bet` AS t " +
            " WHERE  t.status=0 and  t.match_id =:match_id AND t.quotation_type = 1 GROUP BY win_team_id,odds) AS ta" +
            " where ta.win_team_id is Null ORDER BY ta.betAmount DESC,ta.betCount DESC LIMIT 0,1",nativeQuery = true)
    List<List<Object>>   findFullCourtHotOddsByMatchIdAndTieAll(@Param("match_id") String matchId);

    //让分
    @Query(value = "SELECT  ta.give_points,ta.odds,ta.bond,ta.surplusBond,ta.win_team_id FROM (select give_points,win_team_id,odds,IFNULL(sum(betAmount),0) AS betAmount,IFNULL(sum(betCount),0) AS betCount," +
            "sum(bond) as bond,sum(surplus_bond) as surplusBond  from `quotation_bet` AS t " +
            " WHERE  t.status=0 and  t.match_id =:match_id AND t.quotation_type = 2 GROUP BY give_points,win_team_id,odds) AS ta" +
            "  ORDER BY give_points desc,ta.betAmount DESC,ta.betCount DESC",nativeQuery = true)
    List<List<Object>>  findRFHotOddsByMatchIdAll(@Param("match_id") String matchId);

    //大小
    @Query(value = "SELECT  ta.specific_size,ta.odds,ta.bond,ta.surplusBond,ta.compare_flog FROM (select specific_size,compare_flog,odds,IFNULL(sum(betAmount),0) AS betAmount,IFNULL(sum(betCount),0) AS betCount," +
            "sum(bond) as bond,sum(surplus_bond) as surplusBond  from `quotation_bet` AS t " +
            " WHERE  t.status=0 and  t.match_id =:match_id AND t.quotation_type = 3 GROUP BY specific_size ,odds,compare_flog ) AS ta" +
            " ORDER BY specific_size asc, ta.betAmount DESC,ta.betCount DESC ",nativeQuery = true)
    List<List<Object>> findDxHotOddsByMatchIdAll(@Param("match_id") String matchId);


    BetEntity findFirstByTxId(String txId);
}
