package com.betswap.market.client.user.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//转账返回结果数据
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransMoneyDto {
    private String hash; //哈希值
    private String result;  //结果
    private String code;    //状态码
    private String message; //信息

}
