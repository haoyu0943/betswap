package com.betswap.market.client.user.dto;

import com.betswap.market.infrastruture.utils.bigDecimalFormat.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class IndexPageDTO {

    private String userId;
    private String userName;
    private String userPhone;           //用户手机
    private String userAvatar;          //用户封面
    private String userIntroduction;    //用户介绍

    private String shopRankStatus; //商城等级状态

    private String moviesRankStatus;   //影视基金等级

    private String onlineDays;  //上线天数

    private Long latestOnlineTime;     //最新上线时间

    private Integer privilege;     //用户特权级 0.各项功能正常 1.禁止登陆 2.掌柜 3.管理员

    private Long followGoodsNumber; //收藏的商品数量

    private Long followShopNumber; //收藏的店铺数量

    private Long buyFundNumber; //购买基金数

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal totalBETRevenue;  //BET总收益

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = BigDecimalFormatSerializer.class)
    private BigDecimal totalUSDTRevenue;  //USDT总收益

    private int waitPayOrder;  //待付款数量

    private int waitReceiveOrder;  //待收货数量

    private int  finishOrder;//已完成订单数量

    private int  problemOrder;//问题订单数量

    private int  totalOrder; //全部订单数量


    private MerchantApplyRecordDTO recordDto;//店铺申请信息


}
