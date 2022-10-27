package com.betswap.market.client.common.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommonMd5 {
    @NotBlank(message = "verification_0006")
    @ApiParam(required = true)
    private String  keyToken;    //加密参数
}
