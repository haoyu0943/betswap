package com.betswap.market.infrastruture.quotation.dao;

import com.betswap.market.infrastruture.quotation.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao extends JpaRepository<OrderEntity,String> {

    OrderEntity findFirstById(String orderId);

    @Query(value = "select * from tbl_order where user_id= :user_id and id= :id limit 0,1 " ,nativeQuery = true)
    OrderEntity findByUserIdAndOrderId(@Param("user_id")String userId,@Param("id") String orderId);

    @Query(value = "SELECT * FROM `tbl_order`  where  id in (:ids) order by id asc  ",nativeQuery = true)
    List<OrderEntity> findByIdIn(@Param("ids")List<String> asList);
}
