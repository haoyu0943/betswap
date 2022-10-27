package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.RoleBETEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleBETDao extends JpaRepository<RoleBETEntity,Long> {

    //根据用户代理等级及data_type（商城&影视）查询bet 的返回比率
    @Query(value = "select rewards_ratio from sys_role_bet where agent_rank = :agent_rank and  data_type = :data_type ",nativeQuery = true)
    Integer findRewardsRatioByAgentRankAndDataType(@Param("agent_rank")Integer shopRank, @Param("data_type") Integer dataType);

    //根据用户代理等级及data_type（商城&影视）查询bet 日释放率
    @Query(value = "select daily_release_ratio from sys_role_bet where agent_rank = :agent_rank and  data_type = :data_type ",nativeQuery = true)
    Integer findDailyRewardsRatioByAgentRankAndDataType(@Param("agent_rank")Integer agent_rank, @Param("data_type") Integer dataType);


    @Query(value = "select online_days from sys_role_bet where agent_rank = :agent_rank and  data_type = :data_type ",nativeQuery = true)
    Integer findOnlineDaysByAgentRankAndDataType(@Param("agent_rank")Integer shopRank, @Param("data_type") Integer dataType);

    RoleBETEntity findFirstBySubordinateCount(int count);

    RoleBETEntity findFirstByAgentRank(String agentRank);

}