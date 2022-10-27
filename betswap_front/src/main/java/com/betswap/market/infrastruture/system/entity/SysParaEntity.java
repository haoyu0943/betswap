package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sys_para")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysParaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paraDesc;
    private String paraValue;
}



