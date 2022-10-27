/**
 * 
 */
package com.yijiang.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.yijiang.core.LoginInterceptor;
import com.yijiang.model.*;
import com.yijiang.model.selfdefined.modelinfoModel;
import com.yijiang.service.SystemService;
import com.yijiang.util.*;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class SystemController extends Controller {

	private SystemService systemService = SystemService.getInstance();

	public void userrank() {
		SysuserModel ry = getSessionAttr("userSession");
		setAttr("user", ry);
		setAttr("menuflg", "2");
		SysUserRankModel mod_sc=systemService.doSetUserRank("1",ry);
		SysUserRankModel mod_xs=systemService.doSetUserRank("2",ry);
		setAttr("mod_sc",mod_sc);
		setAttr("mod_xs",mod_xs);
		//List<RoleTbtModel> rolelist_sc=RoleTbtModel.dao.find("select * from sys_role_tbt where data_type=1 order by agent_rank asc");
		List<RoleTbtModel> rolelist_xs=RoleTbtModel.dao.find("select * from sys_role_tbt where data_type=2 order by agent_rank asc");
		//setAttr("rolelist_sc",rolelist_sc);
		setAttr("rolelist_xs",rolelist_xs);
		renderJsp("userrank.jsp");
	}

	public void updateUserRank(){
		boolean flg=true;
		String id=getPara("id");
		String fldname=getPara("fldname");
		String updateVal=getPara("updateVal");
		String oldval="";
		try {
			SysUserRankModel mod = SysUserRankModel.dao.findById(id);
			if(mod.getStr(fldname)!=null){
				oldval=mod.getStr(fldname);
			}
			mod.set(fldname, updateVal);
			mod.set("update_time", new Date().getTime());
			mod.update();
			SysuserModel ry=getSessionAttr("userSession");
			String ip=(String)getSession().getAttribute("clientIP");
			String content=fldname+":"+oldval+"-->"+updateVal;
			SyslogController.WriteSysLogDetail(SyslogController.XG, id,"sys_user_rankset",ip,ry,content);
		}
		catch(Exception e){
			e.printStackTrace();
			flg=false;
		}
		renderJson(flg);
	}

	public void roletbt(){
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user",ry);
		List<RoleTbtModel> rolelist_sc=RoleTbtModel.dao.find("select * from sys_role_tbt where data_type=1 order by agent_rank asc");
		List<RoleTbtModel> rolelist_xs=RoleTbtModel.dao.find("select * from sys_role_tbt where data_type=2 order by agent_rank asc");
		setAttr("menuflg","4");
		setAttr("rolelist_sc",rolelist_sc);
		setAttr("rolelist_xs",rolelist_xs);
		renderJsp("role_tbt.jsp");
	}

	public void updateRoleTbt() {
		String agentRank = getPara("agentRank");
		String dataType = getPara("dataType");
		String fld = getPara("fld");
		String val = getPara("val");
		String fldname= TzStringUtils.propertyToField(fld);
		String oldval="";
		String id="";
		String sql="select "+fldname+" from  sys_role_tbt where agent_rank='" + agentRank + "' and data_type="+dataType;
		Record rec=Db.findFirst(sql);
		if(rec!=null&&rec.getStr(fldname)!=null){
			oldval=rec.getStr(fldname);
			id=rec.getStr("id");
		}
		sql = "update sys_role_tbt set " + fldname+ "='" + val + "' where agent_rank='" + agentRank + "' and data_type="+dataType;

		Db.update(sql);
		if(!oldval.equals(val)){
			SysuserModel ry=getSessionAttr("userSession");
			String ip=(String)getSession().getAttribute("clientIP");
			String content=fldname+":"+oldval+"-->"+val;
			SyslogController.WriteSysLogDetail(SyslogController.XG, id,"sys_role_tbt",ip,ry,content);
		}
		renderJson();
	}

	public void syspara(){
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user",ry);
		List<Record> paralist=Db.find("select * from sys_para order by id asc");
		setAttr("menuflg","5");
		setAttr("paralist",paralist);
		renderJsp("syspara.jsp");
	}

	public void updateSysPara() {
		String id = getPara("id");
		String val = getPara("val");
		String sql="select para_value from  sys_para where id='" +id + "'";
		Record rec=Db.findFirst(sql);
		String oldval="";
		if(rec!=null&&rec.getStr("para_value")!=null){
			oldval=rec.getStr("para_value");
		}
		sql = "update sys_para set para_value='" + val + "' where id='" +id + "'";
		Db.update(sql);
		if(!oldval.equals(val)){
			SysuserModel ry=getSessionAttr("userSession");
			String ip=(String)getSession().getAttribute("clientIP");
			String content="sys_para:"+oldval+"-->"+val;
			SyslogController.WriteSysLogDetail(SyslogController.XG, id,"sys_para",ip,ry,content);
		}
		renderJson();
	}

	public void adlist() {
		setAttr("menuflg","6");
		SysuserModel ry=getSessionAttr("userSession");
		List<DictionaryModel> advtypelist=BusinessUtils.qryDiclistByDictype("adType");
		setAttr("advtypelist", advtypelist);
		setAttr("user", ry);
		renderJsp("advlist.jsp");
	}

	public void qryPageAdv(){
		String gjc=getPara("gjc");
		String start=getPara("start");
		String length=getPara("length");
		String type=getPara("type");
		String draw=getPara("draw");

		HashMap jMap = new HashMap();
		jMap.put("gjc", gjc);
		jMap.put("type", type);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		Map<String, Object> map =systemService.findPageAdvByPara(jMap);
		renderJson(map);
	}

	public void crtOreditAdv(){
		TimeZone.setDefault(TimeZone.getTimeZone("GTM+8"));
		String id= getPara("id");
		AdvertisementModel advertisementModel = getModel(AdvertisementModel.class);
		if(StrKit.notBlank(id)){
			advertisementModel=AdvertisementModel.dao.findFirst("select * from advertisement where id=?",id);
		}
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(advertisementModel.getTimestamp("time_online")!=null){
			advertisementModel.put("strTimeonline",sdf.format(advertisementModel.getTimestamp("time_online")));
		}
		else{
			advertisementModel.put("strTimeonline","");
		}
		if(advertisementModel.getTimestamp("time_offline")!=null){
			advertisementModel.put("strTimeoffline",sdf.format(advertisementModel.getTimestamp("time_offline")));
		}
		else{
			advertisementModel.put("strTimeoffline","");
		}
		//System.out.println(advertisementModel);
		setAttr("adv", advertisementModel);
		SysuserModel user = getSessionAttr("userSession");
		List<DictionaryModel> advtypelist=BusinessUtils.qryDiclistByDictype("adType");
		setAttr("advtypelist", advtypelist);
		setAttr("id", id);
		setAttr("menuflg", "6");
		setAttr("user", user);
		renderJsp("advinput.jsp");
	}



	public void setAdvTop(){
		String id=getPara("id");
		String topFlg=getPara("topFlg");
		String typeFlg=getPara("typeFlg");
		boolean flg=true;
		String msg="sucess";
		int numlimit=3;
		if(topFlg.equals("1")){
			//先查询是否已经有三个以上置顶了
			int topcount=systemService.findTopAdvCount(typeFlg);
			//获取到系统参数配置
			String tvalue=BusinessUtils.getPara("12");
			if(StringUtils.isNotBlank(tvalue)){
				numlimit=Integer.parseInt(tvalue);
			}
			if(topcount>=numlimit){
				flg=false;
				msg="The amount of top has more than "+numlimit;
			}
			else{
				systemService.setTopAdv(id,Integer.parseInt(topFlg));
			}
		}
		else{
			systemService.setTopAdv(id,Integer.parseInt(topFlg));
		}
		if(flg) {
			String ip = (String) getSession().getAttribute("clientIP");
			SysuserModel ry = getSessionAttr("userSession");
			if(topFlg.equals("1")) {
				//SyslogController.WriteSysLog(SyslogController.ZD, id, "advertisement", ip, ry);
				String content=ry.getStr("user_name")+" set top";
				SyslogController.WriteSysLogDetail(SyslogController.ZD, id,"advertisement",ip,ry,content);
			}
			else{
				//SyslogController.WriteSysLog(SyslogController.QXZD, id, "advertisement", ip, ry);
				String content=ry.getStr("user_name")+" cancel top";
				SyslogController.WriteSysLogDetail(SyslogController.QXZD, id,"advertisement",ip,ry,content);
			}
		}
		setAttr("flg",flg);
		setAttr("message",msg);
		renderJson();
	}

	public void  delAdv(){
		boolean flag = true;
		String id=getPara("id");
		AdvertisementModel advModel=AdvertisementModel.dao.findFirst("select * from advertisement where id=?",id);
		advModel.set("del_flg",1).set("update_time",new Date().getTime());
		flag=advModel.update();
		String ip=(String)getSession().getAttribute("clientIP");
		SysuserModel ry=getSessionAttr("userSession");
		//SyslogController.WriteSysLog(SyslogController.SC,id,"advertisement",ip,ry);
		String content=ry.getStr("user_name")+" delete advertisement record";
		SyslogController.WriteSysLogDetail(SyslogController.SC, id,"advertisement",ip,ry,content);
		setAttr("flag", flag);
		renderJson();

	}

	public void msglist() {
		setAttr("menuflg","7");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("msglist.jsp");
	}
	public void qryPageMsg(){
		String keyword=getPara("gjc");
		String startDate=getPara("startDate");
		String endDate=getPara("endDate");
		String length=getPara("length");
		String start=getPara("start");
		String draw=getPara("draw");

		HashMap jMap = new HashMap();
		jMap.put("gjc", keyword);
		jMap.put("startDate", startDate);
		jMap.put("endDate", endDate);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		Map<String, Object> map =systemService.findPageMsgByPara(jMap);
		renderJson(map);
	}

	public void crtOreditMsg(){
		String messageid= getPara("messageid");
		SysmsgModel sysmsgModel = getModel(SysmsgModel.class);
		if(StrKit.notBlank(messageid)){
			sysmsgModel=SysmsgModel.dao.findFirst("select * from sys_msg where message_id=?",messageid);
		}
		setAttr("msg", sysmsgModel);
		SysuserModel user = getSessionAttr("userSession");
		setAttr("messageid", messageid);
		setAttr("menuflg", "7");
		setAttr("user", user);
		renderJsp("msginput.jsp");
	}

	public void  delMsg(){
		boolean flag = true;
		String messageid=getPara("messageid");
		String sql="delete from sys_msg where message_id=?";
		Db.update(sql,messageid);
		String ip=(String)getSession().getAttribute("clientIP");
		SysuserModel ry=getSessionAttr("userSession");
		//SyslogController.WriteSysLog(SyslogController.SC,messageid,"sys_msg",ip,ry);
		String content=ry.getStr("user_name")+" delete sys message record";
		SyslogController.WriteSysLogDetail(SyslogController.SC, messageid,"sys_msg",ip,ry,content);
		setAttr("flag", flag);
		renderJson();

	}

	//消息贴文--start

	@Clear(LoginInterceptor.class)
	public void indexArticle(){
		setAttr("lan",getPara("lan"));
		renderJsp("/article/index.jsp");
	}

	@Clear(LoginInterceptor.class)
	public void detailArticle(){

		SysArticleModel sysArticleModel = getModel(SysArticleModel.class);
		sysArticleModel=SysArticleModel.dao.findFirst("select * from sys_article where id=?",getPara("id"));
		/*
		String tmps=sysArticleModel.getStr("content").trim();
		tmps.replaceAll("\r"," ");
		tmps.replaceAll("\n"," ");
		tmps.replaceAll("\t"," ");
		String shows="&nbsp;&nbsp;&nbsp;&nbsp;";
		int p=tmps.indexOf(" ");
		while(p>0){
			shows+=tmps.substring(0,p).trim()+"<br>&nbsp;&nbsp;";
			tmps=tmps.substring(p).trim();
			p=tmps.indexOf(" ");
		}
		//最后没空格的那段要加上
		shows+=tmps;
		System.out.println(shows);
		//为空格加回车换行
		sysArticleModel.set("content",shows);
		 */
		setAttr("sysArticle",sysArticleModel);
		renderJsp("/article/newsDetail.jsp");
	}

	public void articlelist() {
		setAttr("menuflg","18");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("articlelist.jsp");
	}

	@Clear(LoginInterceptor.class)
	public void qryPageArticle(){
		String keyword=getPara("gjc");
		String startDate=getPara("startDate");
		String endDate=getPara("endDate");
		String length=getPara("length");
		String start=getPara("start");
		String draw=getPara("draw");

		HashMap jMap = new HashMap();
		jMap.put("gjc", keyword);
		jMap.put("startDate", startDate);
		jMap.put("endDate", endDate);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		Map<String, Object> map =systemService.findPageArticleByPara(jMap);
		renderJson(map);
	}

	@Clear(LoginInterceptor.class)
	public void qryDetialByArticleId(){
		HashMap jMap = new HashMap();
		String id= getPara("id");
		SysArticleModel sysArticleModel = getModel(SysArticleModel.class);
		if(StrKit.notBlank(id)){
			sysArticleModel=SysArticleModel.dao.findFirst("select * from sys_article where id=?",id);
			if(sysArticleModel!=null){
				jMap.put("data", sysArticleModel);
				jMap.put("code",1000);
			}else{
				jMap.put("code",1001);
			}
		}else{
			jMap.put("code",1001);
		}
		renderJson(jMap);
	}

	public void crtOreditArticle(){
		String id= getPara("id");
		SysArticleModel sysArticleModel = getModel(SysArticleModel.class);
		if(StrKit.notBlank(id)){
			sysArticleModel=SysArticleModel.dao.findFirst("select * from sys_article where id=?",id);
		}
		setAttr("msg", sysArticleModel);
		SysuserModel user = getSessionAttr("userSession");
		setAttr("id", id);
		setAttr("menuflg", "18");
		setAttr("user", user);
		renderJsp("articleinput.jsp");
	}

	public void  delArticle(){
		boolean flag = true;
		String id=getPara("id");
		String sql="delete from sys_article where id=?";
		Db.update(sql,id);
		String ip=(String)getSession().getAttribute("clientIP");
		SysuserModel ry=getSessionAttr("userSession");
		//SyslogController.WriteSysLog(SyslogController.SC,messageid,"sys_msg",ip,ry);
		String content=ry.getStr("user_name")+" delete sys article record";
		SyslogController.WriteSysLogDetail(SyslogController.SC, id,"sys_article",ip,ry,content);
		setAttr("flag", flag);
		renderJson();

	}
	//消息贴文--end


	//协议--start

//	@Clear(LoginInterceptor.class)
//	public void indexProtocol(){
//		setAttr("lan",getPara("lan"));
//		renderJsp("/protocol/index.jsp");
//	}
//
	@Clear(LoginInterceptor.class)
	public void detailProtocol(){

		SysProtocolModel sysProtocolModel = getModel(SysProtocolModel.class);
		sysProtocolModel=SysProtocolModel.dao.findFirst("select * from sys_protocol where type_flag=?",getPara("typeFlag"));
		setAttr("sysProtocol",sysProtocolModel);
		renderJsp("/protocol/protocolDetail.jsp");
	}


	@Clear(LoginInterceptor.class)
	public void qryPageProtocol(){
		String keyword=getPara("gjc");
		String startDate=getPara("startDate");
		String endDate=getPara("endDate");
		String length=getPara("length");
		String start=getPara("start");
		String draw=getPara("draw");

		HashMap jMap = new HashMap();
		jMap.put("gjc", keyword);
		jMap.put("startDate", startDate);
		jMap.put("endDate", endDate);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		Map<String, Object> map =systemService.findPageProtocolByPara(jMap);
		renderJson(map);
	}

	public void protocollist() {
		setAttr("menuflg","19");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("protocollist.jsp");
	}


	public void crtOreditProtocol(){
		String id= getPara("id");
		SysProtocolModel sysProtocolModel = getModel(SysProtocolModel.class);
		if(StrKit.notBlank(id)){
			sysProtocolModel=SysProtocolModel.dao.findFirst("select * from sys_protocol where id=?",id);
		}
		setAttr("msg", sysProtocolModel);
		SysuserModel user = getSessionAttr("userSession");
		setAttr("id", id);
		setAttr("menuflg", "19");
		setAttr("user", user);
		renderJsp("protocolinput.jsp");
	}


	public void saveProtocol(){
		String id= getPara("id");
		SysProtocolModel sysProtocolModel = getModel(SysProtocolModel.class);
		if(StrKit.notBlank(id)){
			sysProtocolModel=SysProtocolModel.dao.findFirst("select * from sys_protocol where id=?",id);
			sysProtocolModel.set("title", getPara("title"))
					.set("content", getPara("content")).set("modifier",getPara("userId")).set("if_need_sign",getPara("sign"))
					.set("update_time", new Date().getTime());
			sysProtocolModel.update();
		}else{
			sysProtocolModel.set("id",NumberDefine.getJlbh("P"))
					.set("title", getPara("title")).set("type_flag", getPara("typeFlag"))
					.set("content", getPara("content")).set("user_id",getPara("userId")).set("if_need_sign",getPara("sign"))
					.set("create_time", new Date().getTime());
			sysProtocolModel.save();
		}
		setAttr("code",1000);
		renderJson();
	}

	public void  delProtocol(){
		boolean flag = true;
		String id=getPara("id");
		String sql="delete from sys_protocol where id=?";
		Db.update(sql,id);
		String ip=(String)getSession().getAttribute("clientIP");
		SysuserModel ry=getSessionAttr("userSession");
		//SyslogController.WriteSysLog(SyslogController.SC,messageid,"sys_msg",ip,ry);
		String content=ry.getStr("user_name")+" delete sys protocol record";
		SyslogController.WriteSysLogDetail(SyslogController.SC, id,"sys_protocol",ip,ry,content);
		setAttr("flag", flag);
		renderJson();

	}

	public void  checkProtocolCode(){
		boolean flag = true;
		String typeFlag=getPara("typeFlag");
		SysProtocolModel sysProtocolModel=SysProtocolModel.dao.findFirst("select * from sys_protocol where type_flag=?",typeFlag);
		if(sysProtocolModel!=null){
			flag = false;
		}
		setAttr("flag", flag);
		renderJson();

	}
	public void  qryProtocolContent(){
		String id=getPara("id");
		SysArticleModel sysArticleModel = getModel(SysArticleModel.class);
		if(StrKit.notBlank(id)){
			sysArticleModel=SysArticleModel.dao.findFirst("select * from sys_protocol where id=?",id);
		}
		setAttr("content",sysArticleModel.getStr("content"));
		renderJson();
	}
	//协议--end




	public void dsshoplist() {
		setAttr("menuflg","8");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("dsshoplist.jsp");
	}

	public void shoplist() {
		setAttr("menuflg","8");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("shoplist.jsp");
	}

	public void qryPageDsShop(){
		String gjc=getPara("gjc");
		String start=getPara("start");
		String length=getPara("length");
		String draw=getPara("draw");

		HashMap jMap = new HashMap();
		jMap.put("gjc", gjc);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		//这里查待审的
		jMap.put("status","0");
		Map<String, Object> map =systemService.findPageShopByPara(jMap);
		renderJson(map);
	}

	public void qryPageShop(){
		String gjc=getPara("gjc");
		String start=getPara("start");
		String length=getPara("length");
		String draw=getPara("draw");

		HashMap jMap = new HashMap();
		jMap.put("gjc", gjc);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		//这里查审核通过的
		jMap.put("status","1");
		Map<String, Object> map =systemService.findPageShopByPara(jMap);
		renderJson(map);
	}



	public void imageShow() {
	    String tbl=getPara("tbl");
		String keyfld=getPara("keyfld");
		String keyid=getPara("keyid");
		String urlfld=getPara("urlfld");
		String serviceUrl=getPara("serviceUrl");
		String imgurl="";
		String sql="select "+urlfld+" from "+ tbl+" where "+keyfld+"='"+keyid+"'";
		Record rec=Db.findFirst(sql);
		if(rec!=null){
			imgurl=rec.getStr(urlfld);
		}
	    int imgh=0;
	    int imgw=0;
	    if(rec!=null) {
			try {
				InputStream is = new URL(serviceUrl + "/" + imgurl).openStream();
				BufferedImage sourceImg = ImageIO.read(is);
				imgw = sourceImg.getWidth();
				imgh = sourceImg.getHeight();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		setAttr("imgH",imgh);
		setAttr("imgW",imgw);
		setAttr("imgurl",imgurl);
		renderJsp("showimg.jsp");
	}

	public void dsgoodslist() {
		setAttr("menuflg","14");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		List<DictionaryModel> goodsSeries = DictionaryModel.dao.find("SELECT * FROM dictionary WHERE dic_type=?", "goodsSeries");
		setAttr("goodsSeries",goodsSeries);
		renderJsp("dsgoodslist.jsp");
	}

	public void goodslist() {
		setAttr("menuflg","14");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		List<DictionaryModel> goodsSeries = DictionaryModel.dao.find("SELECT * FROM dictionary WHERE dic_type=?", "goodsSeries");
		setAttr("goodsSeries",goodsSeries);
		renderJsp("goodslist.jsp");
	}

	public void qryPageDsGoods(){
		String gjc=getPara("gjc");
		String gcategory=getPara("gcategory");
		String start=getPara("start");
		String length=getPara("length");
		String draw=getPara("draw");

		HashMap jMap = new HashMap();
		jMap.put("gjc", gjc);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		jMap.put("status","0");
		jMap.put("series",gcategory);
		Map<String, Object> map =systemService.findPageGoodsByPara(jMap);
		renderJson(map);
	}

	public void qryPageGoods(){
		String gjc=getPara("gjc");
		String gcategory=getPara("gcategory");
		String start=getPara("start");
		String length=getPara("length");
		String draw=getPara("draw");
		HashMap jMap = new HashMap();
		jMap.put("gjc", gjc);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		jMap.put("status","1");
		jMap.put("series",gcategory);
		Map<String, Object> map =systemService.findPageGoodsByPara(jMap);
		renderJson(map);
	}


	public void shopbllist(){
		setAttr("menuflg","15");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("shopbllist.jsp");
	}

	public void qryPageTrueShop(){
		String gjc=getPara("gjc");
		String dpxj=getPara("dpxj");
		String start=getPara("start");
		String length=getPara("length");
		String draw=getPara("draw");
		HashMap jMap = new HashMap();
		jMap.put("gjc", gjc);
		jMap.put("dpxj", dpxj);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		//这里查审核通过的
		Map<String, Object> map =systemService.findPageTrueShopByPara(jMap);
		renderJson(map);
	}





	public void  qryarticlecontent(){
		String id=getPara("id");
		SysArticleModel sysArticleModel = getModel(SysArticleModel.class);
		if(StrKit.notBlank(id)){
			sysArticleModel=SysArticleModel.dao.findFirst("select * from sys_article where id=?",id);
		}
		setAttr("content",sysArticleModel.getStr("content"));
		renderJson();
	}


}

