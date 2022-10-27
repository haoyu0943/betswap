package com.betswap.market.client.user.vo.login;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserByPasswordCmd {

    @ApiParam(value = "手机区号，预留字段不用填",required = false)
    String phoneRegionNumber;

    @NotBlank(message = "verification_0001")
    @ApiParam(value = "用户名",required = true)
    String account;

    @NotBlank(message = "verification_0002")
    @ApiParam(value = "密码",required = true)
    String password;
}
