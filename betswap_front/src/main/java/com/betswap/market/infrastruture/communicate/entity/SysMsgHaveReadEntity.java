package com.betswap.market.infrastruture.communicate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_msg_read")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysMsgHaveReadEntity {
    @Id
    private String    userId;            //创建人用户编号
    private Byte[]    state;
}



