package com.betswap.market.client.flow;

public enum TransactionRecordTypeEnum {
    RECHARGE_FB(               0,"法币充值"),
    WITHDRAWAL_FB(           1,"法币提现"),
    RECHARGE_USDT(             2,"USDT充值"),
    WITHDRAWAL_USDT(     3,"USDT转账"),
    RECHARGE_BET( 4,"BET充值"),
    WITHDRAWAL_BET(5,"BET转账"),
    TransactionRecordTypeEnum(6,"商城BET释放"),
    FREE_BET_DYP(7,"电影票BET释放"),
    BUY_SC(8,"商城购买"),
    BUY_DYP(9,"电影票预售"),
//    BUY_DYP(9,"电影票抢购"),
//    BUY_DYP(9,"基金购买"),
//
//    BUY_DYP(9,"收益表"),
    ;

    public final Integer type;
    public final String description;
    TransactionRecordTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
