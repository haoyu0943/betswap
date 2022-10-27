package com.betswap.market.client.user.vo.register;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterUserCmd {
    @NotBlank(message = "verification_0001")
    @ApiParam(required = true)
    private String userName;

    @NotBlank(message = "verification_0002")
    @ApiParam(required = true)
    private String password;


    //@NotBlank(message = "verification_0051")
    //@ApiParam(required = true)
    private String moneyPassword;

    @NotBlank(message = "verification_0003")
    @ApiParam(required = true)
    private String userPhone;

    @NotBlank(message = "verification_0049")
    @ApiParam(required = true)
    private String phoneRegionNumber;

    @NotBlank(message = "verification_0050")
    @ApiParam(required = true)
    private String phoneRegion;


    @NotBlank(message = "verification_0052")
    @ApiParam(required = true)
    private String verificationCode ; //验证码

    @ApiParam(hidden = true)
    private String userAvatar;

    private String  userIntroduction;

    private String invitationCode;//邀请码

}
