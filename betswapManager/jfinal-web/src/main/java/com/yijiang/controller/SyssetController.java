/**
 * 
 */
package com.yijiang.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.config.JFinalConfig;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.yijiang.model.*;
import com.yijiang.model.selfdefined.modelinfoModel;
import com.yijiang.service.SyssetService;
import com.yijiang.service.SystemService;
import com.yijiang.util.*;
import com.yijiang.util.transaction.TransactionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.*;
import io.rong.models.response.*;
import io.rong.models.user.UserModel;


public class SyssetController extends Controller {

	private SyssetService syssetService = SyssetService.getInstance();


	//**********************比赛地址TRX start**********************//

	public void trxAddress(){
		setAttr("menuflg","21");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("trxAddress.jsp");
	}

	public void getTRXPage(){
		Integer pageNum = 	Integer.parseInt(getPara("pageNum"));
		String startDate= 	getPara("startDate");
		String endDate 	= 	getPara("endDate");
		String homeTeam = getPara("homeTeam");
		String guestTeam = getPara("guestTeam");

		String sql = "select home_team,home_team_en,home_team_zht,guest_team,guest_team_en,guest_team_zht,match_time,wallet_address,match_id,status,intogasstatus,into_amount from tbl_match_address where true " ;
		String sqlTotal = "select count(1) as num from tbl_match_address where true " ;
		if(StringUtils.isNotBlank(homeTeam)){
			sql+=" and  CONCAT(home_team,home_team_en,home_team_zht) like '%"+homeTeam+"%'";
			sqlTotal+=" and  CONCAT(home_team,home_team_en,home_team_zht) like '%"+homeTeam+"%'";
		}

		if(StringUtils.isNotBlank(guestTeam)){
			sql+=" and  CONCAT(guest_team,guest_team_en,guest_team_zht) like '%"+guestTeam+"%'";
			sqlTotal+=" and  CONCAT(guest_team,guest_team_en,guest_team_zht) like '%"+guestTeam+"%'";
		}
		if(StringUtils.isNotBlank(startDate)){
			sql+=" and match_time>'"+TmDateUtil.dateToString(startDate,"yyyy-MM-dd").getTime()+"'";
			sqlTotal+=" and match_time>'"+TmDateUtil.dateToString(startDate,"yyyy-MM-dd").getTime()+"'";
		}
		if(StringUtils.isNotBlank(endDate)){
			sql+=" and match_time<'"+TmDateUtil.dateToString(endDate,"yyyy-MM-dd").getTime()+"'";
			sqlTotal+=" and match_time<'"+TmDateUtil.dateToString(endDate,"yyyy-MM-dd").getTime()+"'";
		}
		sql +=	"  order by match_time desc limit "+10*(pageNum-1)+",10";
		List<Record> records = Db.find(sql);
		String tronUrlVal=Db.findFirst("select * from sys_para where id="+22).getStr("para_value");//钱包服务地址
		for(Record rd :records ){
			JSONObject json=TransactionUtils.findTrxBalance(rd.getStr("wallet_address"),tronUrlVal);
			if("SUCCESS".equals(json.getString("code"))){
				rd.set("balance",json.getBigDecimal("amount"));
			}
		}

		Record r = Db.find(sqlTotal).get(0);

		HashMap jMap = new HashMap();
		jMap.put("records",records);
		jMap.put("total",r.getInt("num"));
		renderJson(jMap);
	}

	//**********************比赛地址TRX end**********************//

	public void addressManagement(){
		setAttr("menuflg","20");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("addressManagement.jsp");
	}

	public void  qryAddress(){
		List<Record> list=Db.find("select type,wallet_address from sys_address where ifdel=0  ");
		setAttr("addressLst", list);
		renderJson();
	}


	public void  saveDZ(){
		String addressType=getPara("addressType");
		String dz=getPara("dz");
		String sy=getPara("sy");

		boolean bn=false;
		try {
			 bn=checkSY(dz,sy);
		}catch (Exception e){
			e.printStackTrace();
		}

		if(bn){
			Record old= Db.findFirst("select * from sys_address where type=? and ifdel=0",addressType);
			if(old!=null){
				old.set("ifdel",1).set("update_time",System.currentTimeMillis());
				Db.update("sys_address",old);
			}
			try {
				Record rd= new Record();
				rd.set("id",TmFunctions.getKeyStr("")).set("type",addressType).set("ifdel",0).set("walkey",DesUtil.encrypt(sy)).set("wallet_address",dz).set("create_time", new Date().getTime());
				Db.save("sys_address",rd);
				setAttr("flag", true);
				renderJson();
			} catch (Exception e) {
				e.printStackTrace();
				setAttr("message", "保存失败！");
				setAttr("flag", false);
				renderJson();
			}
		}else{
			setAttr("message", "用户私钥和地址校验失败");
			setAttr("flag", false);
			renderJson();
		}
	}

	//自动生成地址
	public void  saveDZA(){
		String addressType=getPara("addressType");
		String tronUrlVal=Db.findFirst("select * from sys_para where id="+22).getStr("para_value");//钱包服务地址

		JSONObject json=TransactionUtils.createTronAdr(tronUrlVal);
		if("SUCCESS".equals(json.getString("code"))){
			String sy=json.getString("privateKey");
			String dz =json.getString("address");
			Record old= Db.findFirst("select * from sys_address where type=? and ifdel=0",addressType);
			if(old!=null){
				old.set("ifdel",1).set("update_time",System.currentTimeMillis());
				Db.update("sys_address",old);
			}
			try {
				Record rd= new Record();
				rd.set("id",TmFunctions.getKeyStr("")).set("type",addressType).set("ifdel",0).set("walkey",DesUtil.encrypt(sy)).set("wallet_address",dz).set("create_time", new Date().getTime());
				Db.save("sys_address",rd);
				setAttr("flag", true);
				setAttr("sy", sy);
				setAttr("dz", dz);
				renderJson();
			} catch (Exception e) {
				e.printStackTrace();
				setAttr("message", "保存失败！");
				setAttr("flag", false);
				renderJson();
			}

		}else{
			setAttr("message", "生成地址失败");
			setAttr("flag", false);
			renderJson();
		}
	}


	//检查私钥和地址是否匹配
	private boolean  checkSY(String dz, String sy) throws Exception {
		if(StringUtils.isBlank(dz)||StringUtils.isBlank(sy)){
			return false;
		}
		String tronUrlVal=Db.findFirst("select * from sys_para where id="+22).getStr("para_value");//钱包服务地址

		JSONObject json=TransactionUtils.getAddressByPkey(sy,tronUrlVal);
		if("SUCCESS".equals(json.getString("code"))){
			String address=json.getString("address");
			if(address.equals(dz)){
				return  true;
			}
			return  false;
		}else{
			return  false;
		}

	}


	// 平台地址管理 *****************************、


	public void usermgr(){
		setAttr("menuflg","10");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("usermgr.jsp");
	}

	public void codemgr(){
		setAttr("menuflg","11");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		//查找代码表
		String sql="select count(*), CONCAT(dic_type,'#',dic_type_describe) as codetbl from dictionary "+
				"group by CONCAT(dic_type,'#',dic_type_describe) order by CONCAT(dic_type,'#',dic_type_describe)";
		List<Record> rlist=Db.find(sql);
		for(int i=0;i<rlist.size();i++){
			String tbldesc=rlist.get(i).getStr("codetbl")+"#";
			//System.out.println(tbldesc);
			String[] tbldescarray=tbldesc.split("#");
			//int p=tbldesc.indexOf("#");
			rlist.get(i).set("dic_type",tbldescarray[0]);
			rlist.get(i).set("dic_type_describe",tbldescarray[1]);
			//System.out.println("1="+tbldescarray[0]);
			//System.out.println("2="+tbldescarray[1]);
		}
		//给两个默认的值
		setAttr("dictype", rlist.get(0).getStr("dic_type"));
		setAttr("dictypedescribe", rlist.get(0).getStr("dic_type_describe"));
		setAttr("rlist", rlist);
		renderJsp("codemgr.jsp");
	}


	public void  qryYh(){
		String yhxm = getPara("yhxm");
		String yhdw = getPara("yhdw");
		String sql="SELECT *, COUNT(sxuser.customer_service_id) AS user_num FROM sys_user LEFT JOIN (SELECT customer_service_id FROM `user` WHERE customer_service_id IS NOT NULL) AS sxuser ON sxuser.customer_service_id = sys_user.user_id WHERE del_flg = 0 ";
		if(StringUtils.isNotBlank(yhxm)){
			sql+=" and user_name like '%"+yhxm+"%'";
		}
		if(StringUtils.isNotBlank(yhdw)){
			sql+=" and user_unit like '%"+yhdw+"%'";
		}

		sql+= " GROUP BY sys_user.id order by create_time desc";
		List<SysuserModel> yhlist=SysuserModel.dao.find(sql);
		for(int i=0;i<yhlist.size();i++){
			yhlist.get(i).put("rightcontent",getRightContent(yhlist.get(i).getStr("privilege")));
		}
		setAttr("yhlist",yhlist);
		renderJson();
	}

	public void userServe(){
		String yhxm = getPara("yhxm");
		String yhdw = getPara("yhdw");
		String customer_service_id = getPara("customer_service_id");
		String sql="SELECT *,IF('"+customer_service_id+"'=customer_service_id,1,0) as customer_service_type FROM user WHERE 1=1 ";
		if(StringUtils.isNotBlank(yhxm)){
			sql+=" and user_name like '%"+yhxm+"%'";
		}
		if(StringUtils.isNotBlank(yhdw)){
			sql+=" and user_phone like '%"+yhdw+"%'";
		}
		sql+= " order by id desc";
		List<com.yijiang.model.UserModel> userModels = com.yijiang.model.UserModel.dao.find(sql);
		setAttr("userModels",userModels);
		renderJson();
	}

	public void setuserservice(){
		String service_id = getPara("service_id");
		String user_id = getPara("user_id");
		String sql ="SELECT * FROM sys_user WHERE user_id='"+service_id+"'";
		SysuserModel first = SysuserModel.dao.findFirst(sql);
		String upsql="UPDATE user SET customer_service_id='"+first.getStr("user_id")+"',customer_service_name='"+first.getStr("user_name")+"' WHERE user_id=?";
		Db.update(upsql,user_id);
		renderJson(true);
	}

	public void deluserservice(){
		String user_id = getPara("user_id");
		String upsql="UPDATE user SET customer_service_id='',customer_service_name='' WHERE user_id=?";
		Db.update(upsql,user_id);
		renderJson(true);
	}


	private String getRightContent(String rightstr){
		HashMap map=new HashMap();
		map.put("G01","input movie");
		map.put("G02","set of rules");
		map.put("G06","Rotational advertisement");
		map.put("G07","system message");
		map.put("G18","system article");
		map.put("G08","shop examine and verify");
		map.put("G15","distribution model set");
		map.put("G14","goods examine and verify");
		map.put("G16","blind box set");
		map.put("G17","coupon set");
		map.put("G13","integrative statistics");
		map.put("G10","system user manager");
		map.put("G11","data dictionary set");
		map.put("G12","system log");
        String rs="";
        if(rightstr!=null) {
			if (rightstr.indexOf("#") == -1) {
				if (map.get(rightstr) != null) {
					rs = map.get(rightstr).toString();
				} else {
					rs = "";
				}
			} else {
				String[] rightstrarray = rightstr.split("#");
				for (int i = 0; i < rightstrarray.length; i++) {
					if (map.get(rightstrarray[i]) != null) {
						rs += map.get(rightstrarray[i]).toString() + ",";
					}
				}
				//去掉最后一个符号
				if (rs.length() > 1) {
					rs = rs.substring(0, rs.length() - 1);
				}
			}
		}
		return rs;
	}

	public void delYh(){
		String userid = getPara("userid");
		int flag = Db.update("update sys_user set del_flg=1 where user_id=?",userid);
		String ip=(String)getSession().getAttribute("clientIP");
		SysuserModel ry=getSessionAttr("userSession");
		//SyslogController.WriteSysLog(SyslogController.SC,userid,"sys_user",ip,ry);
		String content=ry.getStr("user_name")+" delete user";
		SyslogController.WriteSysLogDetail(SyslogController.SC, userid,"sys_user",ip,ry,content);
		renderJson(true);
	}

	public void updYh(){
		String userid = getPara("userid");
		SysuserModel ryxx=SysuserModel.dao.findFirst("select * from sys_user where user_id=?",userid);
		setAttr("yhxx",ryxx);
		renderJson();

	}

	public void initMm(){
		String userid = getPara("userid");
		boolean flag=syssetService.initMm(userid);
		String ip=(String)getSession().getAttribute("clientIP");
		SysuserModel ry=getSessionAttr("userSession");
		//SyslogController.WriteSysLog(SyslogController.XG,userid,"sys_user",ip,ry);
		String content=ry.getStr("user_name")+" init password";
		SyslogController.WriteSysLogDetail(SyslogController.XG, userid,"sys_user",ip,ry,content);
		setAttr("flag",flag);
		renderJson();
	}

	public void saveYh(){
		String userid=getPara("userid");
		String yhdw=getPara("yhdw");
		String yhzh=getPara("yhzh");
		String yhsjh=getPara("yhsjh");
		String yhxm=getPara("yhxm");
		String yhqx=getPara("yhqx");
		//System.out.println("yhqx="+yhqx);
		String yhxb=getPara("yhxb");
		String kfjs=getPara("kfjs");
		String ctxPath=getPara("ctxPath");
		boolean flag=false;
		SysuserModel user = getSessionAttr("userSession");
		String ip=(String)getSession().getAttribute("clientIP");
		String errmsg="";
		String sql="";
		String savetype="";
		//修改
		if(StrKit.notBlank(userid)){
			savetype="TMFY";
			//修改前先要判断账号会不会改的和其他人冲突
			sql="select count(*) n from sys_user where account='"+yhzh+"' and user_id<>'"+userid+"'";
			Record rec=Db.findFirst(sql);
			if(rec.getInt("n")>0){
				errmsg="same account already exists";
			}
			else{
				List<modelinfoModel> oldlist=new ArrayList<modelinfoModel>();
				List<modelinfoModel> newlist=new ArrayList<modelinfoModel>();
				SysuserModel ryxx=SysuserModel.dao.findFirst("select * from sys_user where user_id=?",userid);
				oldlist=BusinessUtils.Model2List(ryxx,"SysuserModel");
				ryxx.set("user_unit", yhdw).set("account", yhzh).set("user_phone", yhsjh).set("user_name", yhxm).set("user_sex", yhxb).set("privilege",yhqx)
						.set("update_time", new Date().getTime());
				ryxx.set("type_flg",kfjs);
				flag=ryxx.update();
				newlist=BusinessUtils.Model2List(ryxx,"SysuserModel");
				//SyslogController.WriteSysLog(SyslogController.XG,userid,"sys_user",ip,user);
				String content=BusinessUtils.findDifferent(oldlist,newlist);
				SyslogController.WriteSysLogDetail(SyslogController.XG, userid,"sys_user",ip,user,content);
				if(!kfjs.equals("0")&&ryxx.getStr("rongyu_token")==null) {
					String appKey = "3argexb63g1le";
					String appSecret = "0jMFedQ1MrUMOU";
					String api = "=https://api-cn.ronghub.com";
					String psnimg=ctxPath+"/message/img/icon01.png";
					try {
						appKey = PropertyUtil.getProperty("appKey");
						appSecret = PropertyUtil.getProperty("appSecret");
						api = PropertyUtil.getProperty("api");

					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						//System.out.println("appKey="+appKey);
						//System.out.println("appSecret="+appSecret);
						//System.out.println("api="+api);
						RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret, api);
						User rongCloudUser = rongCloud.user;
						UserModel curuser = new UserModel();
						curuser.setId(userid).setName(yhzh).setPortrait(psnimg);
						TokenResult rongCloudresult = rongCloudUser.register(curuser);
						String rongCloudToken = rongCloudresult.getToken();
						//System.out.println("rongCloudToken="+rongCloudToken);
						if(rongCloudToken!=null) {
							ryxx.set("rongyu_token", rongCloudToken);
							ryxx.update();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		//新建
		else{
			savetype="TADD";
			//新建时要判断是否已经存在相同的账号
			sql="select count(*) n from sys_user where account='"+yhzh+"' and user_id<>'"+userid+"'";
			Record rec=Db.findFirst(sql);
			if(rec.getInt("n")>0){
				errmsg="same account already exists";
			}
			else{
				SysuserModel ryxx=getModel(SysuserModel.class);
				userid= NumberDefine.getJlbh("R");
				ryxx.set("user_id", userid)
					.set("user_unit", yhdw).set("account", yhzh).set("user_phone", yhsjh).set("user_name", yhxm).set("user_sex", yhxb).set("privilege",yhqx)
				    .set("create_time", new Date().getTime()).set("create_user_id",user.getStr("user_id")).set("del_flg",0).set("create_user_name",user.getStr("user_name"));
				ryxx.set("password", Md5Crypt.EncoderByMd5("111111"));
				ryxx.set("type_flg",kfjs);
				flag=ryxx.save();
				//SyslogController.WriteSysLog(SyslogController.XJ,userid,"sys_user",ip,user);
				String content=user.getStr("user_name")+" create user";
				SyslogController.WriteSysLogDetail(SyslogController.XJ, userid,"sys_user",ip,user,content);
				//读写融云的配置
				if(!kfjs.equals("0")) {
					String appKey = "3argexb63g1le";
					String appSecret = "0jMFedQ1MrUMOU";
					String api = "=https://api-cn.ronghub.com";
					try {
						appKey = PropertyUtil.getProperty("appKey");
						appSecret = PropertyUtil.getProperty("appSecret");
						api = PropertyUtil.getProperty("api");
					}
					catch (Exception e) {
						e.printStackTrace();
					}
						try {
							//System.out.println("appKey="+appKey);
							//System.out.println("appSecret="+appSecret);
							//System.out.println("api="+api);
							RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret, api);
							User rongCloudUser = rongCloud.user;
							UserModel curuser = new UserModel().setId(userid).setName(yhzh).setPortrait("_");
							TokenResult rongCloudresult = rongCloudUser.register(curuser);
							System.out.println("code="+rongCloudresult.code);
							System.out.println("msg="+rongCloudresult.msg);
							String rongCloudToken=rongCloudresult.getToken();
							System.out.println("rongCloudToken="+rongCloudToken);
							if(rongCloudToken!=null) {
								ryxx.set("rongyu_token", rongCloudToken);
								ryxx.update();
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
				}
			}
		}
		setAttr("errmsg",errmsg);
		setAttr("flag",flag);
		renderJson();
	}

	public void qryPageCode(){
		String codetype=getPara("codetype");
		String codetypedesc=getPara("codetypedesc");
		String start=getPara("start");
		String length=getPara("length");
		String draw=getPara("draw");
		HashMap jMap = new HashMap();
		jMap.put("codetype", codetype);
		jMap.put("codetypedesc", codetypedesc);
		jMap.put("start",start);
		jMap.put("length",length);
		jMap.put("draw",draw);
		Map<String, Object> map =syssetService.findPageCodeByPara(jMap);
		renderJson(map);
	}

	public void  saveCode(){
		boolean flag = false;
		DictionaryModel dictionaryModel = getModel(DictionaryModel.class);
		String id=getPara("id");
		String codetype=getPara("codetype");
		String codetypedesc=getPara("codetypedesc");
		String newcode=getPara("newcode");
		String newdescribe=getPara("newdescribe");
		String picurl=getPara("picurl");

		SysuserModel ry=getSessionAttr("userSession");
		String ip=(String)getSession().getAttribute("clientIP");
		if(StrKit.isBlank(id)){
			//id= NumberDefine.getJlbh("D");
			dictionaryModel.set("code", newcode)
					.set("dic_describe", newdescribe)
					.set("dic_type", codetype)
					.set("dic_type_describe",codetypedesc)
					.set("picurl",picurl)
					.set("create_time",new Date().getTime());
			flag = dictionaryModel.save();
			//SyslogController.WriteSysLog(SyslogController.XJ, newcode,"dictionary",ip,ry);
			String content=ry.getStr("user_name")+" create code record";
			SyslogController.WriteSysLogDetail(SyslogController.XJ, newcode,"dictionary",ip,ry,content);
		}
		else{
			List<modelinfoModel> oldlist=new ArrayList<modelinfoModel>();
			List<modelinfoModel> newlist=new ArrayList<modelinfoModel>();
			dictionaryModel=DictionaryModel.dao.findById(id);
			oldlist=BusinessUtils.Model2List(dictionaryModel,"DictionaryModel");
			dictionaryModel.set("code", newcode)
					.set("picurl",picurl)
					.set("dic_describe", newdescribe);
			flag = dictionaryModel.update();
			newlist=BusinessUtils.Model2List(dictionaryModel,"MovieFundModel");
			String content=BusinessUtils.findDifferent(oldlist,newlist);
			//SyslogController.WriteSysLog(SyslogController.XG, id,"dictionary",ip,ry);
			SyslogController.WriteSysLogDetail(SyslogController.XG, newcode,"dictionary",ip,ry,content);
		}
		setAttr("id", id);
		setAttr("flag", flag);
		renderJson();
	}

	public void  delCode(){
		String id=getPara("id");
		String sql="delete from dictionary where id=?";
		int flg=Db.update(sql,id);
		SysuserModel ry=getSessionAttr("userSession");
		String ip=(String)getSession().getAttribute("clientIP");
		//SyslogController.WriteSysLog(SyslogController.SC, id,"dictionary",ip,ry);
		String content=ry.getStr("user_name")+" delete code record";
		SyslogController.WriteSysLogDetail(SyslogController.SC, id,"dictionary",ip,ry,content);
		renderJson();
	}

	public void  qryCode(){
		String id=getPara("id");
		DictionaryModel dictionaryModel=DictionaryModel.dao.findFirst("select * from dictionary where id=?",id);
		setAttr("dicmdl", dictionaryModel);
		renderJson();
	}

}

