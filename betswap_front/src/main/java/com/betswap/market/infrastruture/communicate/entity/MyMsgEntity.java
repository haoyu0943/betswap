package com.betswap.market.infrastruture.communicate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

//我的消息
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "my_msg")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMsgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;  //消息接收人
    private String initiator;//消息发起人(消息列表根据人员查询名称和图片)
    private String title;
    private String content;
    private Integer type; //账户消息 ，资产消息
    private Integer readStatus;//读取状态
//    @CreatedDate
    private Long createTime;         //创建时间
}



