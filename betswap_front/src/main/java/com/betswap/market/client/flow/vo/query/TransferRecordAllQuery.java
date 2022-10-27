package com.betswap.market.client.flow.vo.query;

import com.betswap.market.client.common.vo.qry.CommonPageQry;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

//充值转账记录
@EqualsAndHashCode(callSuper=false)
@Data
public class TransferRecordAllQuery extends CommonPageQry {

    @NotBlank(message = "verification_0032")
    @ApiParam(value = "流水类型 1 充值 ,2 转账", required = true)
    private String   type;//收益类型

    @ApiParam(value = "支付类型(逗号分隔的集合) 0法币 1.USDT 2.BET 3.TRX", required = true)
    private String   payType;//收益类型
    @NotBlank(message = "verification_0032")
    @ApiParam(value = "支付结果 0 成功 1.未确认 2.回滚", required = true)
    private String   confirm;//支付结果

}
