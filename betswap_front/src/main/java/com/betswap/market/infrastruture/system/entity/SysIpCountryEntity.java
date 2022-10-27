package com.betswap.market.infrastruture.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sys_ipcountry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysIpCountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  ipStart;

    private String  ipEnd;

    private String  ipStartNumb;

    private String  ipEndNumb;

    private String      countryAbbr;

    private String      countryEn;

    private String      countryCn;

}



