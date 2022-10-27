package com.betswap.market.infrastruture.flow.dao;

import com.betswap.market.infrastruture.flow.entity.RevenueRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface RevenueRecordDao extends JpaRepository<RevenueRecordEntity,Integer> {

    @Query(value = "select * from revenue_record where user_id = :user_id and  pay_type = :pay_type order by create_time desc limit 0,1 ",nativeQuery = true)
    RevenueRecordEntity findFirstByUserIdAndType(@Param("user_id")String userId, @Param("pay_type")Integer payType);

    @Query(value = "select pay_type,sum(amount) from revenue_record where user_id = :user_id group by pay_type ",nativeQuery = true)
    List<List<Object>> findTotalGroupByType(@Param("user_id")String userId);

    @Query(value = "select sum(amount) from revenue_record where user_id = :user_id and pay_type = :pay_type and create_time>=:beginDay and create_time<=:endDay ",nativeQuery = true)
    BigDecimal findTotalByDateAndType(@Param("user_id")String userId,@Param("beginDay") Date beginDay, @Param("endDay")Date endDay,  @Param("pay_type")Integer payType);

    @Query(value = "select sum(amount) from revenue_record where user_id = :user_id and pay_type = :pay_type  ",nativeQuery = true)
    BigDecimal findTotalByType(@Param("user_id")String userId, @Param("pay_type")Integer payType);
}