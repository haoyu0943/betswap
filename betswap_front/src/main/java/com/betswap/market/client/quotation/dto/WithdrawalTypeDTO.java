package com.betswap.market.client.quotation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//提现类型集合
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalTypeDTO {

    private Integer  type;//类型

    private String  name;//名称

}
