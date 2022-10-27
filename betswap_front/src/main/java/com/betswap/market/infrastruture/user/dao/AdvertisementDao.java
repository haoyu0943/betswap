package com.betswap.market.infrastruture.user.dao;

import com.betswap.market.infrastruture.user.entity.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvertisementDao extends JpaRepository<AdvertisementEntity,Integer> {

    @Query(value = "select type_flg,cover_image,link_url,rel_id from advertisement where (top_flg=1 or now() between time_online and time_offline) and type_flg=2 and del_flg=0 order by update_time desc",nativeQuery = true)
    List<Object[]> findAdOnTop();

    @Query(value = "select movie_name,cover_image from movie where type_flg=:type order by issue_date desc limit :limitLeft,:pageSize",nativeQuery = true)
    List findMoviesOfCertainType(@Param("type")int type,@Param("limitLeft")int limitLeft,@Param("pageSize")int pageSize);


}