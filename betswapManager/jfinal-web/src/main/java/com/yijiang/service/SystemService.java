package com.yijiang.service;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yijiang.model.*;
import com.yijiang.util.BusinessUtils;
import com.yijiang.util.NumberDefine;
import com.yijiang.util.TmDateUtil;
import com.yijiang.util.TzStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemService {
    public static SystemService systemService;
    Logger log = Logger.getLogger(SystemService.class);

    public static SystemService getInstance() {
        if (systemService == null) {
            synchronized (SystemService.class) {
                if (systemService == null) {
                    systemService = new SystemService();
                }
            }
        }
        return systemService;
    }

    public SysUserRankModel doSetUserRank(String rankType,SysuserModel ry) {
        SysUserRankModel sysUserRankModel=SysUserRankModel.dao.findFirst("select * from sys_user_rankset where rank_type="+rankType);
        if(sysUserRankModel==null){
            sysUserRankModel=new SysUserRankModel();
            sysUserRankModel.set("id", NumberDefine.getJlbh(""));
            sysUserRankModel.set("user_id", ry.get("user_id")).set("user_name", ry.get("user_name"));
            sysUserRankModel.set("create_time",new Date().getTime());
            //设置default值
            sysUserRankModel.set("numb_ordinary",0);
            sysUserRankModel.set("numb_gold",1);
            sysUserRankModel.set("numb_platinum",20);
            sysUserRankModel.set("numb_diamond",50);
            sysUserRankModel.set("numb_middle",60);
            sysUserRankModel.set("numb_senior",80);
            sysUserRankModel.set("numb_director",80);
            sysUserRankModel.set("rank_type",Integer.parseInt(rankType));
            sysUserRankModel.save();
        }
        return sysUserRankModel;
    }

    public Map<String, Object> findPageAdvByPara(HashMap map) {
        String gjc= TzStringUtils.checkNull(map.get("gjc"));
        String type= TzStringUtils.checkNull(map.get("type"));
        String draw = TzStringUtils.checkNull(map.get("draw"));
        int start = Integer.parseInt((String)map.get("start"));
        int length = Integer.parseInt((String)map.get("length"));
        int pageNo = start /  length + 1;
        String sql_from="from advertisement ";
        String sql_where="where del_flg=0 ";
        if(StringUtils.isNoneBlank(type)){
            sql_where+=" and type_flg="+type;
        }
        if(StringUtils.isNoneBlank(gjc)){
            sql_where+=" and title like '%"+gjc+"%'";
        }
        sql_where+=" order by create_time desc";
        String sql="select * ";
        Page<Record> advPage = Db.paginate(pageNo, length, sql,sql_from+sql_where);
        List<Record> advlist = advPage.getList();
        List<DictionaryModel> advtypelist=BusinessUtils.qryDiclistByDictype("adType");
        for(int i=0;i<advlist.size();i++){
            advlist.get(i).set("rownum",start+i+1);
            advlist.get(i).set("typename", BusinessUtils.getCodename(advlist.get(i).getStr("type_flg"),advtypelist));
        }
        int count=advPage.getTotalRow();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data", advlist);
        info.put("recordsTotal", count);
        info.put("recordsFiltered", count);
        info.put("draw", draw);
        return info;
    }

    public int findTopAdvCount(String typeflg){
        String sql="select * from advertisement where del_flg=0 and type_flg=? and top_flg=1";
        List<AdvertisementModel> advlist=AdvertisementModel.dao.find(sql,typeflg);
        return advlist.size();
    }

    public void setTopAdv(String id,int topFlg){
        AdvertisementModel advModel=AdvertisementModel.dao.findFirst("select * from advertisement where id=?",id);
        advModel.set("top_flg",topFlg);
        advModel.update();
    }

    public Map<String, Object> findPageMsgByPara(HashMap map) {
        String keyword= TzStringUtils.checkNull(map.get("gjc"));
        String startDate= TzStringUtils.checkNull(map.get("startDate"));
        String endDate= TzStringUtils.checkNull(map.get("endDate"));
        String draw = TzStringUtils.checkNull(map.get("draw"));
        int start = Integer.parseInt((String)map.get("start"));
        int length = Integer.parseInt((String)map.get("length"));
        int pageNo = start /  length + 1;
        String sql_where=" from sys_msg  where true ";
        if(StrKit.notBlank(keyword)) {
            sql_where += " and (title like '%" + keyword + "%' or content like '%" + keyword + "%' )";
        }
        if(StrKit.notBlank(startDate)){
            sql_where +=" and (create_time between '"+startDate+"' and '"+endDate+"') ";
        }
        sql_where+=" order by create_time desc";
        String sql="select * ";
        Page<Record> msgPage = Db.paginate(pageNo, length, sql,sql_where);
        List<Record> msglist = msgPage.getList();
        for(int i=0;i<msglist.size();i++){
            msglist.get(i).set("rownum",start+i+1);
        }
        int count=msgPage.getTotalRow();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data", msglist);
        info.put("recordsTotal", count);
        info.put("recordsFiltered", count);
        info.put("draw", draw);
        return info;
    }

    public Map<String, Object> findPageShopByPara(HashMap map) {
        String gjc= TzStringUtils.checkNull(map.get("gjc"));
        String draw = TzStringUtils.checkNull(map.get("draw"));
        String status = TzStringUtils.checkNull(map.get("status"));
        int start = Integer.parseInt((String)map.get("start"));
        int length = Integer.parseInt((String)map.get("length"));
        int pageNo = start /  length + 1;
        String sql_from=" from merchant_apply_record ";
        String sql_where=" where true ";
        if(StrKit.notBlank(gjc)) {
            sql_where += " and (name like '%" + gjc + "%' or content like '%" + gjc + "%' or store_introduction like '%" + gjc + "%' )";
        }
        if(StrKit.notBlank(status)){
            sql_where +=" and status="+status;
        }
        sql_where+=" order by create_time desc";
        String sql="select * ";
        Page<Record> shopPage = Db.paginate(pageNo, length, sql,sql_from+sql_where);
        List<Record> shoplist = shopPage.getList();
        for(int i=0;i<shoplist.size();i++){
            shoplist.get(i).set("rownum",start+i+1);
        }
        int count=shopPage.getTotalRow();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data",shoplist);
        info.put("recordsTotal", count);
        info.put("recordsFiltered", count);
        info.put("draw", draw);
        return info;
    }

    public Map<String, Object> findPageGoodsByPara(HashMap map) {
        String gjc= TzStringUtils.checkNull(map.get("gjc"));
        String draw = TzStringUtils.checkNull(map.get("draw"));
        String status = TzStringUtils.checkNull(map.get("status"));
        String series = TzStringUtils.checkNull(map.get("series"));
        int start = Integer.parseInt((String)map.get("start"));
        int length = Integer.parseInt((String)map.get("length"));
        int pageNo = start /  length + 1;
        String sql_from=" from goods_apply_record a,goods g ";
        String sql_where=" where a.goods_id=g.goods_id";
        if(StrKit.notBlank(gjc)) {
            sql_where += " and (g.goods_name like '%" + gjc + "%' or g.introduce like '%" + gjc + "%')";
        }
        if(StrKit.notBlank(series)){
            sql_where += " and g.series="+series;
        }
        if(StrKit.notBlank(status)){
            sql_where +=" and a.status="+status;
        }
        sql_where+=" order by a.create_time desc";
        String sql="select a.*,g.cover,g.introduce,g.base_price,g.series,g.brand,g.measurement,g.series_name ";
        Page<Record> goodsApplyPage = Db.paginate(pageNo, length, sql,sql_from+sql_where);
        List<Record> goodsApplylist = goodsApplyPage.getList();
        for(int i=0;i<goodsApplylist.size();i++){
            goodsApplylist.get(i).set("rownum",start+i+1);
        }
        int count=goodsApplyPage.getTotalRow();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data",goodsApplylist);
        info.put("recordsTotal", count);
        info.put("recordsFiltered", count);
        info.put("draw", draw);
        return info;
    }

    public Map<String, Object> findPageTrueShopByPara(HashMap map) {
        String gjc= TzStringUtils.checkNull(map.get("gjc"));
        String dpxj= TzStringUtils.checkNull(map.get("dpxj"));
        String draw = TzStringUtils.checkNull(map.get("draw"));
        String status = TzStringUtils.checkNull(map.get("status"));
        int start = Integer.parseInt((String)map.get("start"));
        int length = Integer.parseInt((String)map.get("length"));
        int pageNo = start /  length + 1;
        String sql_from=" from shop ";
        String sql_where=" where true ";
        if(StrKit.notBlank(gjc)) {
            sql_where += " and (name like '%" + gjc + "%' or content like '%" + gjc + "%' or store_introduction like '%" + gjc + "%' )";
        }
        if(StrKit.notBlank(dpxj)) {
            sql_where += " and star_level= '" + dpxj + "'";
        }
        sql_where+=" order by create_time desc";
        String sql="select * ";
        Page<Record> shopPage = Db.paginate(pageNo, length, sql,sql_from+sql_where);
        List<Record> shoplist = shopPage.getList();
        for(int i=0;i<shoplist.size();i++){
            shoplist.get(i).set("rownum",start+i+1);
        }
        int count=shopPage.getTotalRow();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data",shoplist);
        info.put("recordsTotal", count);
        info.put("recordsFiltered", count);
        info.put("draw", draw);
        return info;
    }


    public Map<String, Object> findPageArticleByPara(HashMap map) {
        String keyword= TzStringUtils.checkNull(map.get("gjc"));
        String startDate= TzStringUtils.checkNull(map.get("startDate"));
        String endDate= TzStringUtils.checkNull(map.get("endDate"));
        String draw = TzStringUtils.checkNull(map.get("draw"));
        int start = Integer.parseInt((String)map.get("start"));
        int length = Integer.parseInt((String)map.get("length"));
        int pageNo = start /  length + 1;
        String sql_where=" from sys_article  where true ";
        if(StrKit.notBlank(keyword)) {
            sql_where += " and (title like '%" + keyword + "%' or content like '%" + keyword + "%' )";
        }
        if(StrKit.notBlank(startDate)){
            sql_where +=" and (create_time between '"+startDate+"' and '"+endDate+"') ";
        }
        sql_where+=" order by create_time desc";
        String sql="select * ";
        Page<Record> msgPage = Db.paginate(pageNo, length, sql,sql_where);
        List<Record> msglist = msgPage.getList();
        for(int i=0;i<msglist.size();i++){
            msglist.get(i).set("rownum",start+i+1);
            if("1".equals(msglist.get(i).getStr("type_flag"))){
                msglist.get(i).set("contenttxt", msglist.get(i).getStr("web_url"));
            }else{
                if(msglist.get(i).getStr("content")!=null) {
                    String  context=delHtmlTags(msglist.get(i).getStr("content"));
                    if(context.length()>400){
                        context=context.substring(0,400)+"...";
                    }
//                msglist.get(i).set("contenttxt", delHtmlTags(msglist.get(i).getStr("content")));
                    msglist.get(i).set("contenttxt", context);
                }
                else{
                    msglist.get(i).set("contenttxt", "");
                }
            }
            msglist.get(i).set("content", "");
        }
        int count=msgPage.getTotalRow();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data", msglist);
        info.put("recordsTotal", count);
        info.put("recordsFiltered", count);
        info.put("draw", draw);
        return info;
    }

    public static String delHtmlTags(String htmlStr) {
        //定义script的正则表达式，去除js可以防止注入
        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式，去除标签，只提取文字内容
        String htmlRegex="<[^>]+>";
        //定义空格,回车,换行符,制表符
        String spaceRegex = "\\s*|\t|\r|\n";

        // 过滤script标签
        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        // 过滤style标签
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        // 过滤html标签
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        // 过滤空格等
        htmlStr = htmlStr.replaceAll(spaceRegex, "");
        return htmlStr.trim(); // 返回文本字符串
    }

    public Map<String, Object> findPageProtocolByPara(HashMap map) {
        String keyword= TzStringUtils.checkNull(map.get("gjc"));
        String startDate= TzStringUtils.checkNull(map.get("startDate"));
        String endDate= TzStringUtils.checkNull(map.get("endDate"));
        String draw = TzStringUtils.checkNull(map.get("draw"));
        int start = Integer.parseInt((String)map.get("start"));
        int length = Integer.parseInt((String)map.get("length"));
        int pageNo = start /  length + 1;
        String sql_where=" from sys_protocol  where true ";
        if(StrKit.notBlank(keyword)) {
            sql_where += " and (title like '%" + keyword + "%' or content like '%" + keyword + "%' )";
        }
        if(StrKit.notBlank(startDate)){
            sql_where +=" and (create_time between '"+startDate+"' and '"+endDate+"') ";
        }
        sql_where+=" order by create_time desc";
        String sql="select * ";
        Page<Record> msgPage = Db.paginate(pageNo, length, sql,sql_where);
        List<Record> msglist = msgPage.getList();
        for(int i=0;i<msglist.size();i++){
            msglist.get(i).set("rownum",start+i+1);
            if(msglist.get(i).getStr("content")!=null) {
                msglist.get(i).set("contenttxt", delHtmlTags(msglist.get(i).getStr("content")));
            }
            else{
                msglist.get(i).set("contenttxt", "");
            }
        }
        int count=msgPage.getTotalRow();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data", msglist);
        info.put("recordsTotal", count);
        info.put("recordsFiltered", count);
        info.put("draw", draw);
        return info;
    }
}
