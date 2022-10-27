package com.yijiang.model.selfdefined;

public class movieStatisticDTO {

    public String timestr;
    public int Fiat;
    public int Usdt;

    public String getTimestr() {
        return timestr;
    }

    public int getFiat() {
        return Fiat;
    }

    public int getUsdt() {
        return Usdt;
    }

    public movieStatisticDTO(String str){
        timestr=str;
        Fiat=0;
        Usdt=0;
    }
}
