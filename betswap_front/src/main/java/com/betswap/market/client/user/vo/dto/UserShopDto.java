package com.betswap.market.client.user.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//商铺cmd
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShopDto {
    private String shopId; //商铺id
    private String name;  //店铺名称
    private String content;    //经营范围
    private String storeCover; //店铺封面
    private String storeIntroduction;    //店铺介绍
    private Long followNumer;  //被关注数量
    private String starLevel;  //评分等级

    private Integer ifCollect;  //是否收藏

}
