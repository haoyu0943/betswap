package com.betswap.market.infrastruture.user.dao;

import com.betswap.market.infrastruture.user.entity.UserQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuestionDao extends JpaRepository<UserQuestionEntity,Integer> {
    UserQuestionEntity findByQuestionId(String questionid);
    List<UserQuestionEntity> findByStadu(int stadu);
}
