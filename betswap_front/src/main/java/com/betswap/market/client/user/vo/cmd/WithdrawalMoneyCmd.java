package com.betswap.market.client.user.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

//提现cmd
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawalMoneyCmd {
//    @NotBlank(message = "转出地址不可为空")
//    @ApiParam(required = true)
//    private String toAdd;  //转出地址

    @ApiParam(required = true)
    @Min(value = 0,     message = "verification_0048")
    private BigDecimal amount ;       //转出数量

    private String remark;    //备注

}
