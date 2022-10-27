package com.betswap.market.client.user.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdUserCmd {
    private String id;
    @NotBlank(message = "verification_0037")
    @ApiParam(required = true)
    private String      userId;
    @NotBlank(message = "verification_0038")
    @ApiParam(required = true)
    private String      privilege;           //专栏编号
}
