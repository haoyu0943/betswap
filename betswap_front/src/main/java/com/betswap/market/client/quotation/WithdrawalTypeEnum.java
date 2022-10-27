package com.betswap.market.client.quotation;

public enum WithdrawalTypeEnum {
    WITHDRAWAL_USDT(               1,"USDT"),//支付类型--USDT
    WITHDRAWAL_TRX(             2,"TRX"),//支付类型--BET
    WITHDRAWAL_BET(             3,"BET"),//支付类型--BET
    ;

    public final Integer type;
    public final String description;
    WithdrawalTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
