package com.betswap.market.client.communicate;

//支付类型
public enum MessageReadTypeEnum {
    UNREAD(               0,"未读"),
    READ(             1,"已读"),
    ;

    public final Integer type;
    public final String description;
    MessageReadTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
