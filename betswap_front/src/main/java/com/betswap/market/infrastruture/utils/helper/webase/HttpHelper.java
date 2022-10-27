package com.betswap.market.infrastruture.utils.helper.webase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Slf4j
public class HttpHelper {
    /**
     * post请求
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url,JSONObject json){

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * post请求
     * @param url
     * @return
     */
    public static JSONObject doGet(String url, Map<String, String> param){

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return JSONObject.parseObject(resultString);
    }

    public static JSONObject doGet(String url){
        return doGet(url,null);
    }

    public static String sendRequest(String url, Map<String,Object> map){
        String result = "";
        //CloseableHttpClient：建立一个可以关闭的httpClient
        //这样使得创建出来的HTTP实体，可以被Java虚拟机回收掉，不至于出现一直占用资源的情况。
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            //发送的参数数据
            //设置发送的数据
            StringEntity s = new StringEntity(JSON.toJSONString(map),"utf-8");
            //s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            //获取返回值
            CloseableHttpResponse res = closeableHttpClient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(res.getEntity());// 返回json格式：
                log.info("POST请求返回的数据是："+result);
            }
        }
        catch (Exception e){
            log.info("发生了异常："+e.getMessage());
        }
        finally {
            try {                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String sendRequest(String url, JSONObject jsonObject){
        String result = "";
        //CloseableHttpClient：建立一个可以关闭的httpClient
        //这样使得创建出来的HTTP实体，可以被Java虚拟机回收掉，不至于出现一直占用资源的情况。
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            //发送的参数数据
            //设置发送的数据
            StringEntity s = new StringEntity(jsonObject.toJSONString(),"utf-8");
            //s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            //获取返回值
            CloseableHttpResponse res = closeableHttpClient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(res.getEntity());// 返回json格式：
                log.info("POST请求返回的数据是："+result);
            }
        }
        catch (Exception e){
            log.info("发生了异常："+e.getMessage());
        }
        finally {
            try {                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static JSONObject sendRequestGetStatus(String url, Map<String,Object> map){
//        System.out.println("url:"+url);
//        System.out.println(JSON.toJSONString(map));
        String result = HttpHelper.sendRequest(url,map);
//        System.out.println(result);
        if(!result.equals("")){//http成功
            JSONObject json = JSONObject.parseObject(result);
            //log.info("http response" + result);
            //if(json.getString("status").equals("success")){//状态码正确
            return json;
            //}
        }
        return null;
    }

    public static JSONObject sendRequestGetStatus(String url, JSONObject jsonObject){
        String result = HttpHelper.sendRequest(url,jsonObject);
        if(!result.equals("")){//http成功
            log.info("http response" + result);
            JSONObject json = JSONObject.parseObject(result);
            return json;
        }
        return null;
    }
}
