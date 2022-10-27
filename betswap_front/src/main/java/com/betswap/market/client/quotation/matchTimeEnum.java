package com.betswap.market.client.quotation;

//五大联赛
public enum matchTimeEnum {
    TIME_JT(               1,"今天"),
    TIME_MT(             2,"明天"),
    TIME_HT( 3,"后天"),
    TIME_YZ( 4,"未来一周"),
    TIME_YY( 5,"未来一月"),
    TIME_GY( 6,"更远"),
    ;

    public final Integer type;
    public final String description;
    matchTimeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
