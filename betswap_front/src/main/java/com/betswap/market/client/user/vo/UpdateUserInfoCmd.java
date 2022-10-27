package com.betswap.market.client.user.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class UpdateUserInfoCmd {
    String userName;
    @ApiParam(required = false)
    String userAvatar;
}
