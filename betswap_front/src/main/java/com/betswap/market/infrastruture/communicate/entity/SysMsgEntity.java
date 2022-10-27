package com.betswap.market.infrastruture.communicate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_msg")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysMsgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String messageId;
    private String title;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="Text", nullable=true)
    private String content;
    private String picUrl;

//    @CreatedDate
    private Long   createTime;         //创建时间
    private String      userName;          //创建人姓名
    private String      userId;            //创建人用户编号
}



