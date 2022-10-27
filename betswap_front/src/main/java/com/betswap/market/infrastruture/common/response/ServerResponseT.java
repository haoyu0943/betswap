package com.betswap.market.infrastruture.common.response;

/**
 * @ Description：统一返回类
 * @Version: 1.0
 */
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServerResponseT<T> extends SingleResponse{
    private T data;		//响应数据

    public static <T> ServerResponseT<T> getInstance(Class<T> t) {
        return new ServerResponseT<T>();
    }

    public ServerResponseT<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public ServerResponseT<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public ServerResponseT<T> data(T data){
        this.data = data;
        return this;
    }

    public ServerResponseT<T> responseEnum(ResponseEnum responseEnum){
        this.setCode(responseEnum.code);
        this.setMessage(responseEnum.message);
        return this;
    }

    public ServerResponseT<T> ok(){
        this.setStatus("success");
        responseEnum(ResponseEnum.SUCCESS);
        return this;
    }

    public ServerResponseT<T> failed(){
        this.setStatus("failed");
        responseEnum(ResponseEnum.FAILED);
        return this;
    }

    public T getData(){
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

