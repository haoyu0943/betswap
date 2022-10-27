/**
 * 
 */
package com.yijiang.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.yijiang.core.LoginInterceptor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ChartController extends Controller {

	@Clear(LoginInterceptor.class)
	public void index(){
//		setAttr("moneyType",getPara("moneyType"));
		renderJsp("chart.jsp");
	}


//	@Clear(LoginInterceptor.class)
//	public void getTBTpriceHistory(){
//		List<Object[]> res=new ArrayList<>();
//		for(Record r:Db.find("SELECT * FROM `tbt_price_history` ORDER BY t DESC limit 64")){
//			Object[] tmp = new Object[2];
//			tmp[0]=new Timestamp(r.getLong("t")).toString().substring(2,16); //格式为 22-02-09 22:06
//			tmp[1]=r.getFloat("price");
//			res.add(tmp);
//		}
//		renderJson(res);
//	}
	@Clear(LoginInterceptor.class)
	public void getTBTpriceHistory(){
		renderJson(Db.find("SELECT * FROM `tbt_price_history` ORDER BY t DESC limit 128"));
//		String moneyType=getPara("moneyType");
//		if("USDT".equals(moneyType)){
//			renderJson(Db.find("SELECT * FROM `usdt_price_history` ORDER BY t DESC limit 128"));
//		}else if("BET".equals(moneyType)){//临时用tbt表
//			renderJson(Db.find("SELECT * FROM `tbt_price_history` ORDER BY t DESC limit 128"));
//		}else if("TRX".equals(moneyType)){
//			renderJson(Db.find("SELECT * FROM `trx_price_history` ORDER BY t DESC limit 128"));
//		}

	}

}

