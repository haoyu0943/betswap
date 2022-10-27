package com.betswap.market.client.wallet.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WithdrawalWalletCmd {
    @NotNull(message = "verification_0001")
    @ApiParam(required = true)
    private Integer type;

    @NotBlank(message = "verification_0002")
    @ApiParam(required = true)
    private String moneyPassword;

    @Min(value = 0,     message = "verification_0048")
    @ApiParam(required = true)
    private BigDecimal amount;

    @NotBlank(message = "verification_0036")
    @ApiParam(required = true)
    private String toAddress;

}
