package com.betswap.market.client.user.dto;

import lombok.Data;

@Data
public class RankDetailDTO {

    private int  rank; //等级 0 -6

    private String  rankName ;   //等级名称 普通会员

    private int  upgradeNumber; //升级需要人数

    private int lower;   //直属下级

}
