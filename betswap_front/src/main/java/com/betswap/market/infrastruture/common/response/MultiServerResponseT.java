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
public class MultiServerResponseT<T> extends MultiResponse {
    private Collection<T> data;		//响应数据

    public static <T> MultiServerResponseT<T> getInstance(Class<T> t) {
        return new MultiServerResponseT<T>();
    }

    public MultiServerResponseT<T> page(Integer total,Integer pageSize,Integer pageNum){
        this.setTotal(total);
        this.setPageSize(pageSize);
        this.setPageNum(pageNum);
        return this;
    }

    public MultiServerResponseT<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public MultiServerResponseT<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public MultiServerResponseT<T> data(Collection<T> data){
        this.data = data;
        return this;
    }

    public MultiServerResponseT<T> responseEnum(ResponseEnum responseEnum){
        this.setCode(responseEnum.code);
        this.setMessage(responseEnum.message);
        return this;
    }

    public MultiServerResponseT<T> ok(){
        this.setStatus("success");
        responseEnum(ResponseEnum.SUCCESS);
        return this;
    }

    public MultiServerResponseT<T> failed(){
        this.setStatus("failed");
        responseEnum(ResponseEnum.FAILED);
        return this;
    }

    public Collection<T> getData(){
        return data;
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

