package yijiang.jboot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

public class HttpHelpUtil {
    static Logger logger=Logger.getLogger(HttpHelpUtil.class);


    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static OkHttpClient client = new OkHttpClient();


    public static String sendRequest(String url, JSONObject jsonObject) throws IOException {
        return post(url,jsonObject.toJSONString());
    }

    public static String sendRequest(String url, Map<String, String> map) throws IOException {
        return post(url, com.alibaba.fastjson.JSON.toJSONString(map));
    }

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //post
    public static String postPequestParam(String url, FormBody.Builder params) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static JSONObject sendRequestGetStatus(String url, Map<String, String> map){
        logger.info(url);
        logger.info(com.alibaba.fastjson.JSON.toJSONString(map));
        String result = "";
        try{
            result =HttpHelpUtil.sendRequest(url,map);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
        System.out.println(result);
        logger.info(result);
        if(!result.equals("")){//http成功
            logger.info("http response" + result);
            JSONObject json = JSONObject.parseObject(result);
            //if(json.getString("status").equals("success")){//状态码正确
            return json;
            //}
        }
        return null;
    }

    public static JSONObject sendRequestGetStatus(String url, JSONObject jsonObject){
        String result = "";
        try{
            result = HttpHelpUtil.sendRequest(url,jsonObject);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
        if(!result.equals("")){//http成功
            logger.info("http response" + result);
            JSONObject json = JSONObject.parseObject(result);
            return json;
        }
        return null;
    }


    public static JSONObject sendRequestGetStatus(String url, FormBody.Builder params) {
        String result = "";
        try{
            result = postPequestParam(url,params);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
        if(!result.equals("")){//http成功
            logger.info("http response" + result);
            JSONObject json = JSONObject.parseObject(result);
            return json;
        }
        return null;
    }

    public static JSONObject sendRequestGet2Status(String url,  Map<String, Object> params) {
        String result = "";
        try{
            result = getPequestParam(url,params);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
        if(!result.equals("")){//http成功
            logger.info("http response" + result);
            JSONObject json = JSONObject.parseObject(result);
            return json;
        }
        return null;
    }

    //get
    public static String getPequestParam(String url, Map<String, Object> params) throws IOException {
        url += getParams(params);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public static String getParams(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer("?");
        if (params!=null) {
            for (Map.Entry<String, Object> item : params.entrySet()) {
                Object value = item.getValue();
                if (value!=null) {
                    sb.append("&");
                    sb.append(item.getKey());
                    sb.append("=");
                    sb.append(value);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}
