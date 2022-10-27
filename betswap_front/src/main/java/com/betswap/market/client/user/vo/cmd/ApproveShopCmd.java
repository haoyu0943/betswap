package com.betswap.market.client.user.vo.cmd;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//商铺审批cmd
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproveShopCmd {

    @ApiParam(value = "申请记录id",required = true)
    private String recordId;  //申请记录id
    @ApiParam(value = "审批状态",required = true)
    private Integer status; //审批状态
    @ApiParam(value = "审核意见",required = false)
    private String opinion;    //审核意见
}
