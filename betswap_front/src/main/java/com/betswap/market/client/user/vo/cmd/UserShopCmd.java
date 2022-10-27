package com.betswap.market.client.user.vo.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//商铺cmd
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShopCmd {
    private String name;  //店铺名称
    private String content;    //经营范围
    private String storeCover; //店铺封面
    private String storeIntroduction;    //店铺介绍
    private String  attachsUrl;    //资质证明材料 可上传多张逗号分隔
//    private List<String>  attachsName;    //资质证明材料 可上传多张
}
