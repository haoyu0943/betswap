package com.betswap.market.client.user.enums;

//店铺申请状态
public enum ShopApplyRecordStatusEnum {
    APPLYING(            0,"申请中"),
    ADOPT(          1,"审批通过"),
    REFUSE(             2,"审批拒绝"),
    ;

    public final Integer status;
    public final String description;
    ShopApplyRecordStatusEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }
}