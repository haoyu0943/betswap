package com.betswap.market.infrastruture.communicate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "communicate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunicateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              //id将作为聊天记录的文件名
    private String Creator;       //开启对话的人
    private String Receiver;      //type=1或2时为空，为3时是交易对手方
    private String OrderId;       //售后沟通时所关联的order
    private short type;           //1问题反馈/意见建议，3售后沟通
    private short status;         //售后沟通中，4个bit依次表示提醒Creator,提醒Receiver,是否让平台介入，提醒平台。 （问题反馈/意见建议）中0表示待回复，1表示已回复
    private Long createTime; //创建时间
    private Long updateTime; //最后回复时间
}
