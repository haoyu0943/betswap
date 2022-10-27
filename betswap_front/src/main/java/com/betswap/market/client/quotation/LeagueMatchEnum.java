package com.betswap.market.client.quotation;

//五大联赛
public enum LeagueMatchEnum {
    MATCH_YC(               1,"英超"),
    MATCH_FJ(             2,"法甲"),//--recharge
    MATCH_XJ( 3,"西甲"),
    MATCH_DJ( 4,"德甲"),
    MATCH_YJ( 5,"意甲"),
    ;

    public final Integer type;
    public final String description;
    LeagueMatchEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
