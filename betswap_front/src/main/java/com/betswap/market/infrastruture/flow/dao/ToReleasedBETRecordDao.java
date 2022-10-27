package com.betswap.market.infrastruture.flow.dao;

import com.betswap.market.infrastruture.flow.entity.ToReleasedBETRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


public interface ToReleasedBETRecordDao extends JpaRepository<ToReleasedBETRecordEntity,Integer> {

    @Query(value = "select IFNULL(sum(surplus_number),0) from to_released_bet_record where user_id = :user_id",nativeQuery = true)
    BigDecimal findTotalSurplusNumberByUserId(@Param("user_id")String userId);

    @Query(value = "select total_number from to_released_bet_record where order_id = :order_id limit 1",nativeQuery = true) //选取第一个，代理收益为第2-第n个
    BigDecimal findBETByOrderId(@Param("order_id")String order_id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE from to_released_bet_record where order_id = :orderId",nativeQuery = true)
    void deleteByOrderId(@Param("orderId")String orderId);
}