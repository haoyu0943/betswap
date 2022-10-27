package com.betswap.market.client.user.dto;

import lombok.Data;

@Data
public class CommonUserInfoDTO {
    private String userId;
    private String userName;
    private String userAvatar;
    private String userIntroduction;
    private Integer ifCollectShop; //是否为关注商铺
    private Long  fans;
}
