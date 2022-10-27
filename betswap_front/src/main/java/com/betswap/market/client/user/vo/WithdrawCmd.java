package com.betswap.market.client.user.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawCmd {
    private BigDecimal amount;
    private Integer type;
    private String address;
}
