package com.betswap.market.infrastruture.communicate.dao;

import com.betswap.market.infrastruture.communicate.entity.CommunicateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommunicationDao extends JpaRepository<CommunicateEntity,Long> {

    @Query(value = "select u.user_name,c.type,c.order_id,c.id,c.status from communicate c inner join user u where creator=:UserId and type=3 and receiver=u.user_id order by update_time DESC limit :limitStart,10" ,nativeQuery = true)
    List<List<Object>> findByCreator(@Param("UserId")String UserId,@Param("limitStart")int limitStart);
    @Query(value = "select u.user_name,c.type,c.order_id,c.id,c.status from communicate c inner join user u where receiver=:UserId and creator=u.user_id order by update_time DESC limit :limitStart,10" ,nativeQuery = true)
    List<List<Object>> findByReceive(@Param("UserId")String UserId,@Param("limitStart")int limitStart);
    @Query(value = "SELECT ttmp.id,creator,u.user_name,order_id,type,update_time from " +
            "(SELECT tmp.id,u.user_name as creator,tmp.receiver,order_id,type,update_time from " +
            "(SELECT id,creator,receiver,order_id,type,update_time FROM `communicate` WHERE (type!=3 and status=0) or (type=3 and status&4 and status&8=0)) tmp " +
            "INNER JOIN user u on u.user_id=creator)ttmp " +
            "LEFT JOIN user u on u.user_id=receiver " +
            "order by update_time DESC limit :limitStart,10",nativeQuery = true)
    List<List<Object>> findByToDo(@Param("limitStart")int limitStart);
    @Query(value = "SELECT ttmp.id,creator,u.user_name,order_id,type,update_time from " +
            "(SELECT tmp.id,u.user_name as creator,tmp.receiver,order_id,type,update_time from " +
            "(SELECT id,creator,receiver,order_id,type,update_time FROM `communicate` WHERE (type!=3 and status=1) or (type=3 and status&4 and status&8)) tmp " +
            "INNER JOIN user u on u.user_id=creator)ttmp " +
            "LEFT JOIN user u on u.user_id=receiver " +
            "order by update_time DESC limit :limitStart,10",nativeQuery = true)
    List<List<Object>> findByDone(@Param("limitStart")int limitStart);
    @Query(value = "select COUNT(*) from communicate where creator=:UserId and type=3" ,nativeQuery = true)
    int countByCreator(@Param("UserId")String UserId);
    @Query(value = "select COUNT(*) from communicate where receiver=:UserId",nativeQuery = true)
    int countByReceive(@Param("UserId")String UserId);
    @Query(value = "select COUNT(*) from communicate where (type!=3 and status=0) or (type=3 and status&4 and status&8=0)",nativeQuery = true)
    int countByToDo();
    @Query(value = "select COUNT(*) from communicate where (type!=3 and status=1) or (type=3 and status&4 and status&8)",nativeQuery = true)
    int countByDone();

    @Query(value = "select * from communicate where id=:Id limit 1" ,nativeQuery = true)
    CommunicateEntity findById(@Param("Id")long Id);

    @Query(value = "select id from communicate where order_id=:OrderId limit 1" ,nativeQuery = true)
    Long findIdByOrderId(@Param("OrderId")String OrderId);

    @Query(value = "select id from communicate where creator=:Creator and type=1 limit 1" ,nativeQuery = true)
    Long findUserSupportByCreator(@Param("Creator")String creator);

    @Query(value = "select creator,receiver from communicate where id=:RecordId limit 1" ,nativeQuery = true)
    List<String[]> findRelatedUserIdById(@Param("RecordId")long RecordId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update communicate set status = status|8 where id = :id",nativeQuery = true)
    void AdminDone(@Param("id") long Id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update communicate set status = status&~1 where id=:id",nativeQuery = true)
    void CreatorRead(@Param("id") long Id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update communicate set status = status&~2 where id=:id",nativeQuery = true)
    void ReceiverRead(@Param("id") long Id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update communicate set status = status|4 where id = :id",nativeQuery = true)
    void AskAdminForAfterSale(@Param("id") long Id);

    //保证每个order_id最多创建一个，所以count(*)可以替换count(distinct order_id)
    @Query(value = "select count(*) from communicate where creator=:Creator and type=3" ,nativeQuery = true)
    int findOrderCountByCreator(@Param("Creator")String creator);
}
