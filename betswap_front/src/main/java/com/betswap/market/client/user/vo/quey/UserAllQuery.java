package com.betswap.market.client.user.vo.quey;

import com.betswap.market.infrastruture.common.request.PageConfig;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserAllQuery {
    @NotNull(message = "verification_0043")
    @Min(value = 0,     message = "verification_0044")
    Integer pageNum = PageConfig.pageNum;

    @NotNull(message = "verification_0045")
    @Min(value = 1,     message = "verification_0046")
    @Max(value = 100,   message = "verification_0047")
    Integer pageSize = PageConfig.pageSize;

    Integer  privilegeType;      //用户特权类型
    String  userName;           //用户名称
    String  series;             //系列
    String  plate;             //板块


}
