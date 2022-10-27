package com.betswap.market.infrastruture.common.response;

import lombok.Data;

@Data
public class Response {
    private String status;      //成功 or 失败
    private Integer code;       //响应状态码
    private String message;	    //响应信息

    public Boolean checkSuccess(){
        return getCode().equals(ResponseEnum.SUCCESS.code);
    }
}
