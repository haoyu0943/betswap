package com.betswap.market.client.quotation;

//比赛状态
public enum MatchStatusEnum {
    MATCH_ERR(               0,"比赛异常，说明：暂未判断具体原因的异常比赛，可能但不限于：腰斩、取消等等，建议隐藏处理"),
    MATCH_WKS(               1,"未开赛"),
    MATCH_SBC(             2,"上半场"),//--recharge
    MATCH_ZC(             3,"中场"),//
    MATCH_XBC(             4,"下半场"),//
    MATCH_JSS(             5,"加时赛"),//
    MATCH_DQJZ(             7,"点球决战"),//
    MATCH_YWS( 8,"已完赛"),
    MATCH_TC(             9,"推迟"),//推迟表示比赛进行时间往后
    MATCH_ZD(             10,"中断"),//中断指比赛进行中停止
    MATCH_YZ(             11,"腰斩"),//腰斩指比赛可能赛前或赛中中止
    MATCH_YQX( 12,"取消"),
    MATCH_ZT( 13,"待定"),//待定表示比赛时间未知
    ;

    public final Integer type;
    public final String description;
    MatchStatusEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }
}
