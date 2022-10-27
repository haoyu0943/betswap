package com.betswap.market.client.common.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AttachCmd {
    private String  filename;  //附件名称
    @NotBlank(message = "verification_0005")
    @ApiParam(required = true)
    private String  url;          //附件路径
}
