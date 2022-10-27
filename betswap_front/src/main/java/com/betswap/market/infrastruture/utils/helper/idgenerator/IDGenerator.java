package com.betswap.market.infrastruture.utils.helper.idgenerator;

import com.betswap.market.infrastruture.utils.helper.token.SnowId;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class IDGenerator {
    public static Random rand = new Random();




    public static String PaymentIDGenerate(String userID){
        return  System.currentTimeMillis() / 1000 +""+ userID.substring(userID.length()-4) + String.format("%0" + 5 + "d", rand.nextInt(99999)) + "p";
    }

    public static String WithdrawIDGenerate(String userID){
        return  System.currentTimeMillis() / 1000 +""+ userID.substring(userID.length()-4) + String.format("%0" + 5 + "d", rand.nextInt(99999)) + "w";
    }

    public static String UserIDGenerate(){
        return SnowId.generateID();
    }

    public static String NFTIDGenerate(String nftTemplateId,String nftTemplateNo,Integer nftTemplateNum){
        int digits = 0;
        while(nftTemplateNum!=0){
            nftTemplateNum /= 10;
            digits++;
        }
        if(StringUtils.isNumeric(nftTemplateNo))
            return nftTemplateId + "_" + String.format("%0" + digits + "d", Integer.valueOf(nftTemplateNo));
        else
            return nftTemplateId + "_" + nftTemplateNo;
    }

    public static String NFTDraftIDGenerate(){
        return "draft_" + SnowId.generateID();
    }

    public static String GoodsIDGenerate(String nftId){
        return nftId;
    }

    public static void main(String[] args) {
        System.out.println(NFTIDGenerate("seriesID","1",999));
    }
}
