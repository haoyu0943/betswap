package com.betswap.market.client.communicate;

//支付类型
public enum MessageTypeEnum {
    ACCOUNT(               0,"账户信息"),
    ASSET(             1,"资产"),
    ;

    public final Integer type;
    public final String description;
    MessageTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
