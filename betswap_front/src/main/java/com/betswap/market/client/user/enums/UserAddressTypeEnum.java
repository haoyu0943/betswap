package com.betswap.market.client.user.enums;

//用户收货地址
public enum UserAddressTypeEnum {
    HOME(           0,"家"),
    COMPANY(           1,"公司"),
    SCHOOL( 2,"学校"),
    OTHER( 3,"其他");

    public final Integer status;
    public final String description;
    UserAddressTypeEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }
}
