package com.betswap.market.client.flow;

//流水类型类型
public enum FlowTypeEnum {
    FLOW_BUY(               0,"购买"),
    FLOW_RECHARGE(             1,"充值"),//--recharge
    FLOW_TRANSFER( 2,"提现"),
    FLOW_INCOME( 3,"收益"),
    ;

    public final Integer type;
    public final String description;
    FlowTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
