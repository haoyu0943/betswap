package com.betswap.market.client.wallet.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterWalleAddresstCmd {
    @NotBlank(message = "verification_0001")
    @ApiParam(required = true)
    private String address;

    private String invitationCode;//邀请码

}
