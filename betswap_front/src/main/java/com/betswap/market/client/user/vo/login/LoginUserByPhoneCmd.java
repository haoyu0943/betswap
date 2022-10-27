package com.betswap.market.client.user.vo.login;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserByPhoneCmd {
    @NotBlank(message = "verification_0003")
    @ApiParam(required = true)
    private String userPhone;

    @NotBlank(message = "verification_0049")
    @ApiParam(required = true)
    private String phoneRegionNumber;  //手机区号
}
