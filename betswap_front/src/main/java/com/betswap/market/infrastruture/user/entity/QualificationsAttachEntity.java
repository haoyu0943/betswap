package com.betswap.market.infrastruture.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//资质证明材料
@Entity
@Table(name = "qualifications_attach")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualificationsAttachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String  recordId;   //  申请id
    private String  filename;     //附件名称
    private String  url;          //附件路径
}
