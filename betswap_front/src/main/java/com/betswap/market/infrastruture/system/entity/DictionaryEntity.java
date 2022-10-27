package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


//字典表
@Entity
@Table(name = "dictionary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String dicDescribe;//描述

    private String dicType;//字典类型

    private String dicTypeDescribe;//字典类型描述

    private String    picURL;       //图片

    private Long   createTime;         //创建时间

}



