package com.betswap.market.client.user.enums;

public enum UserPrivilegeEnum {
    NORMAL(           0,"各项功能正常"),
    FREEZE_LOGIN(           1,"禁止登录"),
    SHOPKEEPER( 2,"掌柜"),
    ADMIN( 99,"管理员账号");

    public final Integer status;
    public final String description;
    UserPrivilegeEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }
}
