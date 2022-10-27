package com.betswap.market.client.system.enums;

public enum IPLangEnum {
    zh_CN(           "zh_CN","中文"),
    en_US(           "en_US","英文"),
    zh_TW( "zh_HK","台湾");//繁体

    public final String status;
    public final String description;
    IPLangEnum(String status, String description){
        this.status = status;
        this.description = description;
    }
}
