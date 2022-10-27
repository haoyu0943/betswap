package com.betswap.market.client.flow;

//流水收益类型
public enum RevenueRecordRevenueTypeEnum {
    SHOP_BUY_RELEASED_BET( "0","商城购物BET释放"),
    SHOP_TEAM_RELEASED_BET( "1","商城下级返现BET释放"),
    MOVIES_BUY_RELEASED_BET( "2","影视BET释放"),
    MOVIES_TEAM_RELEASED_BET( "3","影视下级返现BET释放"),
    FUND_BUY_RELEASED_BET( "4","基金购买BET释放"),
    FUND_TEAM_RELEASED_BET( "5","基金下级返现BET释放"),
    REVENUE_FUND( "6","基金收益"),
    REVENUE_BLINDBOX_RECYCLE( "7","盲盒回收收益"),
    MOVIES_FB( "8","电影收益"),
    MOVIES_AGENT_FB( "9","电影代理收益"),
    ;

    public final String type;
    public final String description;
    RevenueRecordRevenueTypeEnum(String type, String description){
        this.type = type;
        this.description = description;
    }
}
