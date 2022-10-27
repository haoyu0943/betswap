package com.betswap.market.infrastruture.common.response;

/**
 * @ Description：统一返回类
 * @Version: 1.0
 */
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
public class MultiServerResponse extends MultiResponse {
    private Collection<Object> data;		//响应数据

    public static MultiServerResponse getInstance() {
        return new MultiServerResponse();
    }

    public MultiServerResponse page(Integer total,Integer pageSize,Integer pageNum){
        this.setTotal(total);
        this.setPageSize(pageSize);
        this.setPageNum(pageNum);
        return this;
    }

    public MultiServerResponse code(Integer code){
        this.setCode(code);
        return this;
    }

    public MultiServerResponse message(String message){
        this.setMessage(message);
        return this;
    }

    public MultiServerResponse data(Collection<Object> data){
        this.data = data;
        return this;
    }

    public MultiServerResponse responseEnum(ResponseEnum responseEnum){
        this.setCode(responseEnum.code);
        this.setMessage(responseEnum.message);
        return this;
    }

    public MultiServerResponse ok(){
        this.setStatus("success");
        responseEnum(ResponseEnum.SUCCESS);
        return this;
    }

    public MultiServerResponse failed(){
        this.setStatus("failed");
        responseEnum(ResponseEnum.FAILED);
        return this;
    }

    @Override
    public String toString() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", this.getCode());
        resultJson.put("message", this.getMessage());
        resultJson.put("data", this.data);
        return resultJson.toString();
    }

    //测试
    public static void main(String[] args) {
    }
}

