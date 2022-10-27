package com.betswap.market.infrastruture.team.dao;

import com.betswap.market.infrastruture.team.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface TeamMemberDao extends JpaRepository<TeamMemberEntity,Integer> {


    TeamMemberEntity findByUserId(String userId);


    int countByParentId(String userId);

    @Query(value ="select count(1) from team_member where team_id=:teamId and to_days(create_time)=to_days(now())",nativeQuery = true)
    int countByTodayAddCount(@Param("teamId")String teamId);


    @Query(value ="select * from  team_member  where team_id=:teamId order by profitusdt desc ",nativeQuery = true)
    List<TeamMemberEntity> findAllByTeamId(@Param("teamId")String teamId);


    @Query(value ="select totalUsdt from  team_summary  where user_id=:user_id ",nativeQuery = true)
    BigDecimal findTotalUsdt(@Param("user_id")String userId);

}
