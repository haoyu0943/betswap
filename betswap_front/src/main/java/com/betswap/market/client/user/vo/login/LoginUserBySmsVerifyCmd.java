package com.betswap.market.client.user.vo.login;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserBySmsVerifyCmd {
    @NotBlank(message = "verification_0003")
    @ApiParam(required = true)
    String userPhone;

    @NotBlank(message = "verification_0049")
    @ApiParam(required = true)
    String phoneRegionNumber;

    @NotBlank(message = "verification_0004")
    @ApiParam(required = true)
    String messageContent;
}
