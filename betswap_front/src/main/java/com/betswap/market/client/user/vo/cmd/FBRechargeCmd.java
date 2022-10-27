package com.betswap.market.client.user.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

//法币充值 cmd
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Builder
public class FBRechargeCmd  {

    @ApiParam(value = "充值金额",required = true)
    @Min(value = 0,     message = "verification_0048")
    private BigDecimal amount ;       //充值金额

    @NotBlank(message = "verification_0034")
    @ApiParam(value = "卡号",required = true)
    private String bankNum;     //卡号

    @NotBlank(message = "verification_0035")
    @ApiParam(value = "密码",required = true)
    private String password;  //密码
    @ApiParam(value = "备注",required = false)
    private String remark;    //备注

}
