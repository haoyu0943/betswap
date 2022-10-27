package com.betswap.market.infrastruture.system.dao;

import com.betswap.market.infrastruture.system.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DictionaryDao extends JpaRepository<DictionaryEntity,Long> {

    List<DictionaryEntity> findAllByDicTypeIn(String[] arr);

    @Query(value = "select dic_describe from dictionary where code=:code and dic_type=:type",nativeQuery = true)
    String queryDicNameByCodeAndType(@Param("code")String code, @Param("type")String type);

    @Query(value = "select * from dictionary where dic_type=:type order by code asc ",nativeQuery = true)
    List<DictionaryEntity> findAllByDicTypeOderByCodeAsc(@Param("type")String type);

    @Query(value = "select * from dictionary where dic_type=:type and code=:code ",nativeQuery = true)
    DictionaryEntity findDescribeByDicTypeAndCode(@Param("type")String type,@Param("code")String code);

}