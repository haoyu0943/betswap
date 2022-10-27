package com.betswap.market.client.quotation.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class QuotationDTO {

    private String  winTeamId; //获胜队伍id(全场时 设置为null 代表平局 )

    private String winTeam; //获胜队伍名称中文

    private String winTeam_zht; //获胜队伍名称繁体

    private String winTeam_en; //获胜队伍名称英文

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal odds;//获胜赔率

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal bond; //保证金

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal surplusBond; //保证金--剩余

    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal surplusUnitAmount; //可投注金额

    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal unitAmount; //总可投注金额

}
