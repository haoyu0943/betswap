package com.betswap.market.infrastruture.utils.transaction;

import com.alibaba.fastjson.JSONObject;
import com.betswap.market.infrastruture.system.dao.SysParaDao;
import com.betswap.market.infrastruture.utils.Md5.MD5Util;
import com.betswap.market.infrastruture.utils.http.HttpHelpUtil;
import okhttp3.FormBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//交易公共类
@Component
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

    //查询BET余额
    public static JSONObject findBetBalance( String address,String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("address",address);
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"getBetBalance",params);
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
    //转账 tbt
    public static JSONObject outToAddressTBT( String pkey, String toAdd , String num,String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pkey",pkey);
            jsonObject.put("toAdd",toAdd);
            jsonObject.put("num",num);
            jsonObject.put("MD5key", MD5Util.md5(pkey+toAdd+num,keystr));
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"outToAddressTbt",params);
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
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"outToAddressBET",params);
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

    //根据私钥获取 地址
    public static JSONObject getAddressByPrivateKey(String tronUrl,String privateKey){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("privateKey",privateKey);
            jsonObject.put("MD5key",MD5Util.md5(privateKey,keystr));
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"getAddressByPrivateKey",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","ERROR");
            json.put("message",e.getMessage());
        }
        return json;
    }




    //生成12个助记词
    public static JSONObject generateMnemonicWords(String tronUrl){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tronState",tronState);
            jsonObject.put("MD5key",MD5Util.md5(tronState,keystr));
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"generateMnemonicWords",params);
        }catch (Exception e){
            e.printStackTrace();
            json.put("code","ERROR");
            json.put("message",e.getMessage());
        }
        return json;
    }


    /**
     *  根据助记词生成钱包
     * @param tronUrl 钱包路径
     * @param mnemonic  空格分隔的十二个助记词
     * @return
     */
    public static JSONObject generateTrxByMnemonic(String tronUrl,String mnemonic){
        JSONObject json = new JSONObject();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mnemonic",mnemonic);
            jsonObject.put("MD5key",MD5Util.md5(mnemonic,keystr));
            FormBody.Builder params = new FormBody.Builder();
            params.add("jsonStr",jsonObject.toJSONString());
            json= HttpHelpUtil.sendRequestGetStatus(tronUrl+"generateTrxByMnemonic",params);
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
