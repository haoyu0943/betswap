package yijiang.jboot.utils.transaction;

import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import yijiang.jboot.comm.Constant;
import yijiang.jboot.utils.HttpHelpUtil;
import yijiang.jboot.utils.MD5Util;

import java.math.BigDecimal;

//交易公共类
public class TransactionUtils {

    private static final String keystr="Yijiang@2021;Hdxjsds";
    private static final String tronState="eSGJZetyySpUndWjMjsds";


    //创建tron钱包地址
    public static JSONObject createTronAdr(String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tronState",tronState);
            jsonObject.put("MD5key",MD5Util.md5(tronState,keystr));
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"createTronAdr",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","ERROR");
            json.put("message",e.getMessage());
        }
        return json;
    }

    //查询usdt余额
    public static JSONObject findUsdtBalance( String address,String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("address",address);
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"getUsdtBalance",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","ERROR");
            json.put("message",e.getMessage());
        }
        return json;
    }


    public static JSONObject findTrxBalance(String walletAddress, String tronUrl) {
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("address",walletAddress);
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"getTrxBalance",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","FILE");
            json.put("message",e.getMessage());
        }
        return json;
    }

    //查询BET余额
    public static JSONObject findBetBalance( String address,String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("address",address);
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"getTrxBalance",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","FILE");
            json.put("message",e.getMessage());
        }
        return json;
    }

    //转账 FB
    public static JSONObject outToFB(String bankNum,String psd, String toBankNum , String num,String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bankNum",bankNum);
            jsonObject.put("psd",psd);
            jsonObject.put("toBankNum",toBankNum);
            jsonObject.put("num",num);
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"outToFB",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","FILE");
            json.put("message",e.getMessage());
        }
        return json;
    }
    //转账 usdt
    public static JSONObject outToAddressUsdt(String pkey, String toAdd , String num,String tronUrl ){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pkey",pkey);
            jsonObject.put("toAdd",toAdd);
            jsonObject.put("num",num);
            jsonObject.put("MD5key", MD5Util.md5(pkey+toAdd+num,keystr));
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"outToAddressUsdt",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","FILE");
            json.put("message",e.getMessage());
        }
        return json;
    }

    public static JSONObject outToAddressTRX(String pkey, String toAdd, String num, String tronUrl) {
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pkey",pkey);
            jsonObject.put("toAdd",toAdd);
            jsonObject.put("num",num);
            jsonObject.put("MD5key", MD5Util.md5(pkey+toAdd+num,keystr));
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"outToAddressTrx",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","FILE");
            json.put("message",e.getMessage());
        }
        return json;
    }

    //转账 bet
    public static JSONObject outToAddressBET( String pkey, String toAdd , String num,String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pkey",pkey);
            jsonObject.put("toAdd",toAdd);
            jsonObject.put("num",num);
            jsonObject.put("MD5key", MD5Util.md5(pkey+toAdd+num,keystr));
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"outToAddressTRX",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","FILE");
            json.put("message",e.getMessage());
        }
        return json;
    }


    //查询交易记录
    public static JSONObject getTransactionById( String txId,String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("txId",txId);
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"getTransactionById",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","ERROR");
            json.put("message",e.getMessage());
        }
        return json;
    }
}

