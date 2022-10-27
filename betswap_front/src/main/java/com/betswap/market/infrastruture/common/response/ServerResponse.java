package com.betswap.market.infrastruture.common.response;

/**
 * @ Description：统一返回类
 * @Version: 1.0
 */
import com.alibaba.fastjson.JSONObject;
import com.betswap.market.infrastruture.utils.language.LanguageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public  class ServerResponse extends SingleResponse {
    Object data;		//响应数据

    public static  ServerResponse getInstance() {
        return new ServerResponse();
    }

    public static  ServerResponse getInstance(ServerResponseT<?> serverResponseT) {
        if(serverResponseT == null) return null;
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(serverResponseT.getStatus());
        serverResponse.setCode(serverResponseT.getCode());
        serverResponse.setMessage(serverResponseT.getMessage());
        serverResponse.setData(serverResponseT.getData());
        return serverResponse;
    }

    public  ServerResponse code(Integer code){
        this.setCode(code);
        return this;
    }

    public  ServerResponse message(String message){
       this.setMessage(LanguageUtils.changeLanguage(message));
       return this;
    }

    public  ServerResponse data(Object data){
        this.data = data;
        return this;
    }



    public  ServerResponse responseEnum(ResponseEnum responseEnum){
        this.setCode(responseEnum.code);
        this.setMessage(LanguageUtils.changeLanguage(responseEnum.message));
        return this;
    }

    public ServerResponse ok(){
        this.setStatus("success");
        responseEnum(ResponseEnum.SUCCESS);
        return this;
    }

    public ServerResponse fail(){
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

}

