package com.betswap.market.client.user.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class UpdUserBankCmd {

    private String cardId;

    @ApiParam(required = true)
    private Boolean defaultCard ;     //是否默认卡

    @ApiParam(required = true)
    private String bank;     //开户银行
    @ApiParam(required = true)
    private String bankNetWork;     //开户网点
    @ApiParam(required = true)
    private String bankNum;     //卡号
    @ApiParam(required = true)
    private String holder; //持有人

}

