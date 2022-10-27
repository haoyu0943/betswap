package com.betswap.market.client.quotation.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

//比赛dto
@Data
@Builder
public class TeamDTO {

    private String name;//名称

    private String cover;//封面

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal odds;//赔率


}
