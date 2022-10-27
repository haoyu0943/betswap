package com.betswap.market.infrastruture.user.dao;

import com.betswap.market.infrastruture.user.entity.QualificationsAttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QualificationsAttachDao extends JpaRepository<QualificationsAttachEntity,Long> {


    @Query(value = "select * from qualifications_attach where record_id = :recordId",nativeQuery = true)
    List<QualificationsAttachEntity> findByRecordId(@Param("recordId") String recordId);

    @Modifying
    @Query(value = "delete from qualifications_attach where record_id = :recordId",nativeQuery = true)
    void delByRecordId(@Param("recordId") String recordId);

}
