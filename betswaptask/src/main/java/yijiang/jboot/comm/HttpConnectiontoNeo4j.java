package yijiang.jboot.comm;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.jfinal.plugin.activerecord.Db;
import org.apache.log4j.Logger;
import yijiang.jboot.model.RelationMdl;
import yijiang.jboot.utils.HttpHelpUtil;
import yijiang.jboot.utils.TmDateUtil;
import yijiang.jboot.utils.TzStringUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class HttpConnectiontoNeo4j {
    private String neo4jurl="";
    Logger logger=Logger.getLogger("KeywordDig");

    public HttpConnectiontoNeo4j(String Neo4jUrl){
        this.neo4jurl=Neo4jUrl;
    }

    public void addtoNeo4j(List<RelationMdl> rellist) {
        String methodname="addtoGraphDB";
        String ids="";
        JSONObject json = new JSONObject();
        json.put("token", TzStringUtils.Md5Encrypt(methodname));
        json.put("method",methodname);
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<rellist.size();i++){
            JSONObject subjson = new JSONObject();
            subjson.put("id1",rellist.get(i).id1);
            subjson.put("id2",rellist.get(i).id2);
            subjson.put("infotype1",rellist.get(i).infotype1);
            subjson.put("infotype2",rellist.get(i).infotype2);
            subjson.put("infovalue1",rellist.get(i).infovalue1);
            subjson.put("infovalue2",rellist.get(i).infovalue2);
            jsonArray.add(subjson);
            ids+="'"+rellist.get(i).id+"',";
        }
        json.put("data",jsonArray);
        //System.out.println(json.toJSONString());
        //int rescode=doPost(json.toJSONString(),methodname);//成功的话更新ifesproc 、esproctime
        try {
            //JSONObject jResult = JSON.parseObject(TmFunctions.sendtoAPI(neo4jurl + methodname, json, "UTF-8"));
            JSONObject jResult = HttpHelpUtil.sendRequestGetStatus(neo4jurl + methodname, json);
            if (jResult.getIntValue("code") == 1000) {
                if (!ids.equals("")) {
                    Db.update("update tbl_gx_relation set ifhavetrans=1, transtime=? where id in (" + ids + "'')", TmDateUtil.dateTotimestamp(new Date()));
                    showmsg("addtoNeo4j成功加入" + rellist.size() + "条");
                }
            } else {
                showmsg("addtoNeo4j返回异常代码:" + +jResult.getIntValue("code") + ":" + jResult.getString("message"));
            }
        }
        catch (Exception e) {
            showmsg("发生异常:"+e.getMessage());
            e.printStackTrace();
        }
    }

    private void showmsg(String msg){
        System.out.println(msg);
        logger.debug(msg);
    }

    //用作通用方法供其他方法调用
    private int doPost(String jsonstr,String method) {
        OutputStreamWriter out = null;
        HttpURLConnection conn;
        String strurl=neo4jurl+method;
        //System.out.println("url="+strurl);
        int rescode=0;
        try {
            URL url = new URL(strurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(jsonstr);
            out.close();
            rescode=conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return rescode;
    }
}
