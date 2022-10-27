package com.yijiang.controller;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.yijiang.model.SysLogModel;
import com.yijiang.model.SysUserRankModel;
import com.yijiang.model.SysuserModel;
import com.yijiang.util.NumberDefine;
import com.yijiang.util.TmDateUtil;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class SyslogController extends Controller {
	
	private static SysLogModel xtlog = new SysLogModel();
	//日志常用的关键词和备注
	public static String DL="login";
	public static String TC="login out";
	public static String XJ="new record";
	public static String XG="edit record";
	public static String SC="delete record";
	public static String SHTG="pass";
	public static String SHTH="review return";
	public static String ZD="set top";
	public static String QXZD="cancel top";
	public static String SHANGJ="grounding";
	public static String XIAJ="undercarriage";

	
	public static void WriteSysLog(String keyword, String recid, String rectbl, String uip, SysuserModel ryxx){
		xtlog.set("id",NumberDefine.getKqjlbh("Z"));
        xtlog.set("opt_user_id",ryxx.getStr("user_id"));
        xtlog.set("opt_user_name",ryxx.getStr("user_name"));
        xtlog.set("keyword",keyword);
        xtlog.set("opt_ip",uip);
        xtlog.set("opt_key_id",recid);
        xtlog.set("opt_table",rectbl);
		xtlog.set("opt_time",System.currentTimeMillis());
		xtlog.save();
	}

    public static void WriteSysLogDetail(String keyword, String recid, String rectbl, String uip, SysuserModel ryxx,String content){
	  xtlog.set("id",NumberDefine.getKqjlbh("Z"));
	  xtlog.set("opt_user_id",ryxx.getStr("user_id"));
	  xtlog.set("opt_user_name",ryxx.getStr("user_name"));
	  xtlog.set("keyword",keyword);
	  xtlog.set("opt_ip",uip);
	  xtlog.set("opt_key_id",recid);
	  xtlog.set("opt_table",rectbl);
	  xtlog.set("content",content);
	  xtlog.set("opt_time",System.currentTimeMillis());
	  xtlog.save();
    }

	public void log(){
		setAttr("menuflg","12");
		SysuserModel ry=getSessionAttr("userSession");
		setAttr("user", ry);
		renderJsp("log.jsp");
	}


	public void getSysLog(){
		Integer pageNum = 	Integer.parseInt(getPara("pageNum"));
		String startDate= 	getPara("startDate");
		String endDate 	= 	getPara("endDate");
		String optKey = getPara("opt_key_id");
		if (!optKey.equals("")) {
			optKey="'%" + optKey + "%'";
		}

		String sql = "select * from sys_log where true " ;
		if(!optKey.equals(""))	sql+=" and opt_key_id like "+optKey;
		if(!startDate.equals(""))	sql+=" and opt_time>'"+startDate+"'";
		if(!endDate.equals(""))	sql+=" and opt_time<'"+endDate+"'";
		sql +=	"  order by opt_time desc limit "+20*(pageNum-1)+",20";
		List<SysLogModel> mod = SysLogModel.dao.find(sql);

		Record r = Db.find("select COUNT(*) from sys_log where true "
				+((optKey.equals(""))?"":(" and opt_key_id like "+optKey))
				+((startDate.equals(""))?"":(" and opt_time>'"+startDate+"'"))
				+((endDate.equals(""))	?"":(" and opt_time<'"+endDate+"'"))).get(0);

		HashMap jMap = new HashMap();
		jMap.put("loglist",mod);
		jMap.put("total",r.getInt("COUNT(*)"));
		renderJson(jMap);
	}




}
