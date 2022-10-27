package com.betswap.market.client.quotation;

//订单状态
public enum OrderStatusEnum {
    ONGOING(             0,"订单进行中"),
    WAITPAY(             1,"待结账"),//-管理端录入比赛结果 发起支付请求（改为了后台定时抓取比赛信息直接修改结果）
    FINISHED(           2,"订单已完成"),//-支付完成
    CANCEL(             3,"订单已取消"),
    PAYFILE(             4,"结账失败"),
    ORDER_CLOSE(             5,"订单超时关闭"),
    ORDER_INCHAIN(             6,"订单上链中"),// 根据hash txid 去查询 结果
    ORDER_INCHAIN_FAIL(             7,"订单上上链失败"),// 根据 txid 和次数判断 我这边定时 1分钟查一次 判断3次 差不多有3分钟 结果还是没有的话就是失败
    ;

    public final Integer status;
    public final String description;
    OrderStatusEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }
    public static String status2description(Integer status){
        for(OrderStatusEnum e:OrderStatusEnum.values())
            if(e.status.equals(status))
                return e.description;
        return "";
    }

}
