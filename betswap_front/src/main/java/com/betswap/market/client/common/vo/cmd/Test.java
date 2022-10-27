package com.betswap.market.client.common.vo.cmd;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class Test extends CommonMd5 {

    private Integer  count;       //数量

    private String accountType;

    private BigDecimal groupPrice;          //拼团价格

}
