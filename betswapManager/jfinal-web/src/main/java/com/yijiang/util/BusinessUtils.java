package com.yijiang.util;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.*;
import com.yijiang.model.*;
import com.yijiang.model.selfdefined.modelinfoModel;
import org.apache.commons.lang3.CharUtils;
import org.codehaus.plexus.util.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BusinessUtils {

    //查询系统配置表的参数
	public static String  getPara(String paraid) {
		String sql="select para_value from sys_para where id="+paraid;
		Record rec= Db.findFirst(sql);
		if(rec==null){
			return "";
		}
		else{
			return rec.getStr("para_value");
		}
	}

	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		List<T> dest = (List<T>) in.readObject();
		return dest;
	}

	public static List<modelinfoModel> Model2List(Model mdl,String mdlname) {
		List<modelinfoModel> list=new ArrayList<modelinfoModel>();
		Table table=null;
		if(mdlname.equals("AdvertisementModel")){
			table = TableMapping.me().getTable(((AdvertisementModel)mdl).dao.getClass());
		}
		else if(mdlname.equals("DictionaryModel")){
			table = TableMapping.me().getTable(((DictionaryModel)mdl).dao.getClass());
		}
		else if(mdlname.equals("RoleTbtModel")){
			table = TableMapping.me().getTable(((RoleTbtModel)mdl).dao.getClass());
		}
		else if(mdlname.equals("SysmsgModel")){
			table = TableMapping.me().getTable(((SysmsgModel)mdl).dao.getClass());
		}
		else if(mdlname.equals("SysArticleModel")){
			table = TableMapping.me().getTable(((SysArticleModel)mdl).dao.getClass());
		}
		else if(mdlname.equals("SysProtocolModel")){
			table = TableMapping.me().getTable(((SysProtocolModel)mdl).dao.getClass());
		}
		else if(mdlname.equals("SysuserModel")){
			table = TableMapping.me().getTable(((SysuserModel)mdl).dao.getClass());
		}
		else if(mdlname.equals("SysUserRankModel")){
			table = TableMapping.me().getTable(((SysUserRankModel)mdl).dao.getClass());
		}
		else{

		}
		if(table!=null) {
			HashMap map = new HashMap();
			Map<String, Class<?>> m = table.getColumnTypeMap();
			for (Map.Entry<String, Class<?>> entry : m.entrySet()) {
				modelinfoModel mm = new modelinfoModel();
				mm.fldName = entry.getKey();
				mm.fldType = entry.getValue().getName();
				System.out.println(mm.fldType);
				if (mm.fldType.equals("java.lang.Integer")) {
					//System.out.println("int="+mm.fldName);
					if(mdl.getStr(mm.fldName)!=null) {
						mm.fldValue = String.valueOf(mdl.getInt(mm.fldName));
					}
				}
				else if (mm.fldType.equals("java.sql.Timestamp")) {
					if(mdl.getTimestamp(mm.fldName)!=null) {
						mm.fldValue = TmDateUtil.timestamp2Str(mdl.getTimestamp(mm.fldName));
					}
				}
				else if (mm.fldType.equals("java.math.BigDecimal")) {
					if(mdl.getBigDecimal(mm.fldName)!=null) {
						mm.fldValue = mdl.getBigDecimal(mm.fldName).toString();
					}
				}
				else if (mm.fldType.equals("java.lang.Long")) {
					if(mdl.getLong(mm.fldName)!=null) {
						mm.fldValue = mdl.getLong(mm.fldName).toString();
					}
				}
				else {
					if(mdl.getStr(mm.fldName)!=null) {
						mm.fldValue = mdl.getStr(mm.fldName);
					}
				}
				list.add(mm);
			}
		}
		return list;
	}

	public static String  findDifferent(List<modelinfoModel> list1,List<modelinfoModel> list2) {
		String rs="";
		for(int i=0;i<list1.size();i++){
			for(int j=0;j<list2.size();j++){
				if(list1.get(i).fldName.equals(list2.get(j).fldName)){
					if(!list1.get(i).fldValue.equals(list2.get(j).fldValue)){
						rs+=list1.get(i).fldName+":"+list1.get(i).fldValue+"-->"+list2.get(i).fldValue+";";
					}
					break;
				}
			}
		}
		return rs;
	}

	public static String getCodename(String code,List<DictionaryModel> list){
		if(code==null||list==null||list.size()==0){
			return "";
		}
		else{
			String rs="";
			for(int i=0;i<list.size();i++){
				if(code.equals(list.get(i).getStr("code"))){
					rs=list.get(i).getStr("dic_describe");
					break;
				}
			}
			return rs;
		}
	}

	public static List<DictionaryModel>  qryDiclistByDictype(String dictype){
		return DictionaryModel.dao.find("select * from dictionary where dic_type=? order by code",dictype);
	}


}
