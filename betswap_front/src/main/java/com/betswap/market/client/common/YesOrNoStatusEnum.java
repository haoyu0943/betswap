package com.betswap.market.client.common;

//商品拼团状态
public enum YesOrNoStatusEnum {
    NO(          0,  "否"),
    YES(          1,  "是"),
    ;

    public final Integer status;
    public final String description;

    private YesOrNoStatusEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }
}
