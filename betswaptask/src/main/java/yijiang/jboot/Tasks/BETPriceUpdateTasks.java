package yijiang.jboot.Tasks;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.mysql.cj.xdevapi.JsonArray;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;


//将TBT价格写入数据库，用于绘制K线
public class BETPriceUpdateTasks implements Runnable{

    @Override
    public void run(){
        getPriceInfo("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
    }

    private void getPriceInfo(String contractAddr) {

        long start = Db.find("select t from tbt_price_history order by t desc limit 1").get(0).getLong("t");


        // 获取token地址
        //String authHost = "https://api.pancakeswap.info/api/v2/tokens/" + contractAddr;
        String authHost = "https://apilist.tronscan.io/api/justswap/kline?token_address=" + contractAddr +
                "&granularity=1h&time_start="+start+"&time_end="+System.currentTimeMillis()/1000;


        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(authHost).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "PostmanRuntime/7.28.1");//不加这句，会返回403
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            connection.disconnect();

            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            JSONArray data = jsonObject.getJSONArray("data");

            for(int i=0;i<data.size();i++){
                try{
                    JSONObject o = data.getJSONObject(i);
                    Db.update("insert into tbt_price_history values ("+o.getLong("t")+
                            ","+ o.getFloat("o")+
                            ","+ o.getFloat("c")+
                            ","+ o.getFloat("h")+
                            ","+ o.getFloat("l")+
                            ")");
                }catch (Exception e){
                    continue;
                }
            }
        } catch (Exception e) {
            connection.disconnect();
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }

    }

}
