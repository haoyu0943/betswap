package com.betswap.market.infrastruture.common.response;

/**
 * @ Description：统一返回类
 * @Version: 1.0
 */
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MultiResponse extends Response{
    private Integer total;
    private Integer pageNum = 0;
    private Integer pageSize = 30;
}

