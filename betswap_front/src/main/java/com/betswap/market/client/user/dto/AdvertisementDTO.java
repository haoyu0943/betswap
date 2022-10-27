package com.betswap.market.client.user.dto;

import lombok.Builder;
import lombok.Data;

//影视基金个人中心
@Data
@Builder
public class AdvertisementDTO {

    private Integer     typeFlg;            //1商城的2电影的
    private String      linkUrl;       //链接的地址
    private String      coverImage;    //广告图片
    private String     relId;              //关联的商品编号或者电影编号
}
