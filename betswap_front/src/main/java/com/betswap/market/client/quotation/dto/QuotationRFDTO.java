package com.betswap.market.client.quotation.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

// 让分和比大小盘口展示
@Data
public class QuotationRFDTO {


    private String givePointsLeft;  //让分分数/比大小（左侧）+1 -1 >1 <1

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal oddsLeft;//获胜赔率（左侧）

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal bondLeft; //保证金（左侧）

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal surplusBondLeft; //保证金--剩余


    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal surplusUnitAmountLeft; //可投注金额

    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal unitAmountLeft; //总可投注金额



    private List<QuotationDTO> quotationsLeft; //左侧 展出窗口

    private String givePointsRight;  //让分分数/比大小（右侧）

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal oddsRight;//获胜赔率（右侧）

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal bondRight; //保证金（右侧）

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal surplusBondRight; //保证金--剩余（右侧）


    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal surplusUnitAmountRight; //可投注金额

    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal unitAmountRight; //总可投注金额


    private List<QuotationDTO> quotationsRight; //右侧 展出窗口

    private List<QcDTO>  qcDTO;//左右list合并


}
