package com.betswap.market.client.quotation.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;

//转账页面
@Data
public class USDTMessageDTO {

    private String userName; //用户名称

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal betAmount;//转账金额

    private String userAddress ; //用户钱包地址

    private String ptAddress ; //用户钱包地址

    private String usdtContract ; //usdt合约

    private String usdtAbi ; //usdtABI

    private String betContract ; //bet合约

    private String betAbi ; //bet合约
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private BigDecimal fee;//费率-手续费 TRX

}
