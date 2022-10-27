package com.betswap.market.client.user.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

//转账cmd
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransMoneyCmd {
    @NotBlank(message = "verification_0036")
    @ApiParam(required = true)
    private String toAdd;  //转出地址

    @ApiParam(required = true)
    @Min(value = 0,     message = "verification_0048")
    private BigDecimal amount ;       //转出数量

    private String remark;    //备注

}
