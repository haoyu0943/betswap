package com.betswap.market.client.user.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IDCheckCmd {
    @NotBlank(message = "verification_0032")
    @ApiParam(required = true)
    String IDCard;

    @NotBlank(message = "verification_0033")
    @ApiParam(required = true)
    String name;
}
