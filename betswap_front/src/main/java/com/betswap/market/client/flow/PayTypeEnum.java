package com.betswap.market.client.flow;

//支付类型
public enum PayTypeEnum {
    PAY_FB(               0,"法币"),
    PAY_USDT(             1,"USDT"),
    PAY_BET( 2,"BET"),
    ;

    public final Integer type;
    public final String description;
    PayTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
