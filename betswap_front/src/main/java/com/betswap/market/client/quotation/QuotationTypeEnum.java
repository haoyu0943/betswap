package com.betswap.market.client.quotation;

//开盘类型
public enum QuotationTypeEnum {
    QUOTATION_QC(               1,"全场"),
    QUOTATION_RF(             2,"让分"),//--recharge
    QUOTATION_DX( 3,"大小"),
    ;

    public final Integer type;
    public final String description;
    QuotationTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
