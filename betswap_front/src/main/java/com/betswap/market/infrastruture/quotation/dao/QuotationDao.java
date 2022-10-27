package com.betswap.market.infrastruture.quotation.dao;

import com.betswap.market.infrastruture.quotation.entity.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface QuotationDao extends JpaRepository<QuotationEntity,String> {

    @Query(value = "SELECT MAX(odds),win_team_id FROM `tbl_quotation`  where  quotation_type=1  and match_id= :match_id GROUP BY win_team_id  ",nativeQuery = true)
    List<List<Object>>  findFullCourtMaxoddsByMatchId(@Param("match_id") String matchId);

    @Query(value = "SELECT odds,sum(bond),sum(surplus_bond) FROM `tbl_quotation`  where  quotation_type=1  and status=0  and match_id= :match_id and win_team_id = :win_team_id GROUP BY odds order by odds desc  ",nativeQuery = true)
    List<List<Object>>   findWinTeamQutationByQC(@Param("match_id") String matchId, @Param("win_team_id") String teamId);

    @Query(value = "SELECT odds, sum(bond),sum(surplus_bond) FROM `tbl_quotation`  where  quotation_type=1  and status=0  and match_id= :match_id and win_team_id is null GROUP BY odds  order by odds desc",nativeQuery = true)
    List<List<Object>> findTieTeamQutationByQC(@Param("match_id") String matchId);

    @Query(value = "SELECT give_points,odds,sum(bond),sum(surplus_bond),win_team_id  FROM `tbl_quotation`  " +
            "where  quotation_type=2  and status=0  and match_id= :match_id GROUP BY give_points ,win_team_id,odds ORDER BY give_points desc, odds desc  ",nativeQuery = true)
    List<List<Object>> findWinTeamQutationByRF(@Param("match_id") String matchId);

    @Query(value = "SELECT specific_size,odds,sum(bond),sum(surplus_bond),compare_flog  FROM `tbl_quotation`" +
            " where  quotation_type=3  and status=0  and match_id= :match_id GROUP BY specific_size ,odds,compare_flog ORDER BY specific_size desc, odds desc  ",nativeQuery = true)
    List<List<Object>> findWinTeamQutationByDX(@Param("match_id") String matchId);

    @Query(value = "SELECT * FROM `tbl_quotation`  where  quotation_type=1 and surplus_bond>0  and match_id= :match_id and odds = :odds  and win_team_id = :win_team_id order by create_time asc limit 0,1  ",nativeQuery = true)
    QuotationEntity findFirstQuotationForQC(@Param("match_id") String matchId,@Param("odds")  BigDecimal odds, @Param("win_team_id") String winTeamId);

    @Query(value = "SELECT * FROM `tbl_quotation`  where  quotation_type=2 and surplus_bond>0  and match_id= :match_id and odds = :odds  and win_team_id = :win_team_id   and give_points = :give_points  order by create_time asc limit 0,1  ",nativeQuery = true)
    QuotationEntity findFirstQuotationForRF(@Param("match_id") String matchId,@Param("odds")  BigDecimal odds, @Param("win_team_id") String winTeamId,@Param("give_points")  Float givePoints);

    @Query(value = "SELECT * FROM `tbl_quotation`  where  quotation_type=3 and surplus_bond>0  and match_id= :match_id and odds = :odds   " +
            " and compare_flog = :compare_flog  and specific_size = :specific_size  order by create_time asc limit 0,1  ",nativeQuery = true)
    QuotationEntity findFirstQuotationForDX(@Param("match_id") String matchId,@Param("odds")  BigDecimal odds, @Param("compare_flog") String compareFlog, @Param("specific_size") Float specificSize);

    QuotationEntity findFirstById(String quotationId);

    @Query(value = "select IFNULL(sum(bond),0) from tbl_quotation where user_id = :user_id and status=:status",nativeQuery = true)
    BigDecimal findTotalLockingUsdt(@Param("user_id")String userId,@Param("status") Integer status);

    @Query(value = "SELECT * FROM `tbl_quotation`  where  quotation_type=1 and surplus_bond>0  and match_id= :match_id and odds = :odds  and win_team_id is null order by create_time asc limit 0,1  ",nativeQuery = true)
    QuotationEntity findFirstQuotationForQCPJ(@Param("match_id") String matchId,@Param("odds")  BigDecimal odds);

    @Modifying
    @Query(value = "update tbl_quotation set status=:status where match_id= :match_id " ,nativeQuery = true)
    int updateStatusByMatchId(@Param("match_id")String matchId, @Param("status")Integer status);

    @Query(value = "select IFNULL(count(1),0),IFNULL(sum(profit_amount),0) from tbl_quotation where user_id= :user_id and  status=:status",nativeQuery = true)
    List<List<Object>> findTotalSituation(@Param("user_id")String userId, @Param("status")Integer status);

    QuotationEntity findFirstByTxId(String txId);


    @Query(value = "SELECT * FROM `tbl_quotation`  where  quotation_type=1  and status=0  and surplus_bond>0  and match_id= :match_id and odds = :odds  and win_team_id = :win_team_id order by create_time asc   ",nativeQuery = true)
    List<QuotationEntity> findQuotationForQC(@Param("match_id") String matchId,@Param("odds")  BigDecimal odds, @Param("win_team_id") String winTeamId);

    @Query(value = "SELECT * FROM `tbl_quotation`  where  quotation_type=2  and status=0  and surplus_bond>0  and match_id= :match_id and odds = :odds  and win_team_id = :win_team_id   and give_points = :give_points  order by create_time asc  ",nativeQuery = true)
    List<QuotationEntity> findQuotationForRF(@Param("match_id") String matchId,@Param("odds")  BigDecimal odds, @Param("win_team_id") String winTeamId,@Param("give_points")  Float givePoints);

    @Query(value = "SELECT * FROM `tbl_quotation`  where  quotation_type=3  and status=0  and surplus_bond>0  and match_id= :match_id and odds = :odds   " +
            " and compare_flog = :compare_flog  and specific_size = :specific_size  order by create_time asc ",nativeQuery = true)
    List<QuotationEntity> findQuotationForDX(@Param("match_id") String matchId,@Param("odds")  BigDecimal odds, @Param("compare_flog") String compareFlog, @Param("specific_size") Float specificSize);

    @Query(value = "SELECT * FROM `tbl_quotation`  where  quotation_type=1 and status=0 and surplus_bond>0  and match_id= :match_id and odds = :odds  and win_team_id is null order by create_time asc  ",nativeQuery = true)
    List<QuotationEntity> findQuotationForQCPJ(@Param("match_id") String matchId,@Param("odds")  BigDecimal odds);

    @Query(value = "SELECT * FROM `tbl_quotation`  where  id in (:ids) order by id asc  ",nativeQuery = true)
    List<QuotationEntity>  findByIdIn(@Param("ids") List<String> ids);
}
