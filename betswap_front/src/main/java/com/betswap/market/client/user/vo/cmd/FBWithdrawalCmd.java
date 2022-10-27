package com.betswap.market.client.user.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

//法币充值 cmd
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FBWithdrawalCmd {

    @ApiParam(value = "提现金额",required = true)
    @Min(value = 0,     message = "verification_0048")
    private BigDecimal amount ;       //充值金额

    @NotBlank(message = "verification_0034")
    @ApiParam(value = "卡号",required = true)
    private String bankNum;     //卡号

    @ApiParam(value = "备注",required = false)
    private String remark;    //备注

}
