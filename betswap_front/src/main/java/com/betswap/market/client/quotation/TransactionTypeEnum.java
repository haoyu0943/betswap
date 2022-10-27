package com.betswap.market.client.quotation;

//交易类型
public enum TransactionTypeEnum {
    TRANSACTION_BET(               1,"投注"),
    QUOTATION_QUOTATION(             2,"开盘"),//--recharge
    ;

    public final Integer type;
    public final String description;
    TransactionTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
