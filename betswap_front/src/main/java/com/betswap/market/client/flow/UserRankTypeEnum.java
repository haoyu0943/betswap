package com.betswap.market.client.flow;

//支付类型
public enum UserRankTypeEnum {
    RANK_DEFAULT   (   1,"默认类型"),
    RANK_MOVIE(   2,"影视会员类型"),//后续可扩展类型
    ;

    public final Integer type;
    public final String description;
    UserRankTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
