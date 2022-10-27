package com.yijiang.model.selfdefined;

public class moviestatModel {
    public String timestr;
    public int goodsApply;
    public int shopFiat;
    public int shopUsdt;

    public String getTimestr() {
        return timestr;
    }

    public int getGoodsApply() {
        return goodsApply;
    }

    public int getShopFiat() {
        return shopFiat;
    }

    public int getShopUsdt() {
        return shopUsdt;
    }

    public moviestatModel(String str){
        timestr=str;
        goodsApply=0;
        shopFiat=0;
        shopUsdt=0;
    }
}
