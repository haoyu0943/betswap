package com.betswap.market.client.user.vo.login;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserByWechatCmd {
    @NotBlank(message = "verification_0042")
    @ApiParam(required = true)
    String code;
}
