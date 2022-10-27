package com.betswap.market.client.wallet.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterWalletKeyCmd {
    @NotBlank(message = "verification_0001")
    @ApiParam(required = true)
    private String walletName;

    @NotBlank(message = "verification_0002")
    @ApiParam(required = true)
    private String password;


    @NotBlank(message = "verification_0002")
    @ApiParam(required = true)
    private String privateKey;


    private String invitationCode;//邀请码


    private String passwordPrompt ;//密码提示

}
