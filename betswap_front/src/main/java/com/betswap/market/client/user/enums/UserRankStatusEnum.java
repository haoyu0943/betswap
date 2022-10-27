package com.betswap.market.client.user.enums;

//用户影视基金/商城等级
public enum UserRankStatusEnum {
    ORDINARY(           1,"普通"),
    GOLD(               2,"黄金"),
    PLATINUM(           3,"铂金"),
    DIAMOND(            4,"钻石"),
    INTERMEDIATE_PARTNER( 5,"中级合伙人"),
    SENIOR_PARTNER(     6,"高级合伙人"),
    DIRECTOR_PARTNER(   7,"董事合伙人");

    public final Integer status;
    public final String description;
    UserRankStatusEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }
}
