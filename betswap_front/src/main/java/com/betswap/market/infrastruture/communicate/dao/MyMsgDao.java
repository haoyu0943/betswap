package com.betswap.market.infrastruture.communicate.dao;

import com.betswap.market.infrastruture.communicate.entity.MyMsgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMsgDao extends JpaRepository<MyMsgEntity,Long> {

    long countByUserIdAndReadStatus(String userId, int type);

    MyMsgEntity findFirstByUserIdAndId(String userId,Long id);
}
