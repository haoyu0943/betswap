package com.betswap.market.client.quotation;

//收益类型
public enum ProfitTypeEnum {
    BET_PROFIT(               1,"投注收益"),//收益类型--投注收益
    QUOTATION_PROFIT(             2,"开盘收益"),//收益类型--开盘收益
    AGENT_PROFIT( 3,"代理收益"),//收益类型--代理收益
    ;

    public final Integer type;
    public final String description;
    ProfitTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
