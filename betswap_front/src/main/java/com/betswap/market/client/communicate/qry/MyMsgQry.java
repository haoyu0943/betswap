package com.betswap.market.client.communicate.qry;

import com.betswap.market.client.common.vo.qry.CommonPageQry;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

//我的消息查询
@EqualsAndHashCode(callSuper=false)
@Data
public class MyMsgQry extends CommonPageQry {
    @ApiParam(value = "消息类型 0 账户消息 ，1 资产消息", required = false)
    private String   type;//消息类型
}
