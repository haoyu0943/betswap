/**
 * 
 */
package com.yijiang.controller;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.yijiang.common.ConstantList;
import com.yijiang.core.LoginInterceptor;
import com.yijiang.model.AdvertisementModel;
import com.yijiang.model.SysuserModel;
import com.yijiang.model.selfdefined.modelinfoModel;
import com.yijiang.util.BusinessUtils;
import com.yijiang.util.Md5Crypt;
import com.yijiang.util.MyCaptchaRender;

import java.util.List;


public class LoginController extends Controller{
	
	@Clear(LoginInterceptor.class)
	public void index(){
		setAttr("test","zhourd");
		renderJsp("login.jsp");
	}

	@Clear(LoginInterceptor.class)
	public void imgCode(){
		MyCaptchaRender mycap=new MyCaptchaRender(100, 30, 4, true);
		mycap.CrtImgcode();
		removeSessionAttr("curimgcode");
		setSessionAttr("curimgcode", mycap.curimgcode);
		render(mycap);
	}
	
	@Clear(LoginInterceptor.class)
	public void doLogin(){
		//System.out.println("================================");
		String ip=(String)getSession().getAttribute("clientIP");
		String xtzh =getPara("xtzh").trim();
 		String xtmm = getPara("xtmm").trim().toUpperCase();
 		String imgcode=getPara("imgcode").trim();
 		String dllx ="";
 		if(StrKit.isBlank(xtzh) ||StrKit.isBlank(xtmm)||StrKit.isBlank(imgcode)){
 			setAttr("flag", 1);
 			setAttr("error", "username or passowrd is blank!");
 		}
 		else{
 			String curimgcode=getSessionAttr("curimgcode");
 			//校验图片验证码
 			boolean ifyzmValid = MyCaptchaRender.validate(curimgcode,imgcode);
 			if(ifyzmValid){
	 			xtmm= Md5Crypt.EncoderByMd5(xtmm);
				SysuserModel ry=null;
	 			String dlsql="select * from sys_user where account=? and password=? ";
	 			ry=SysuserModel.dao.findFirst(dlsql,xtzh,xtmm);
	 			if(ry==null){
					SysuserModel userBySfzh = SysuserModel.dao.findFirst("select * from sys_user where account=?",xtzh);
	 				if(userBySfzh == null){
	 					setAttr("flag", 1);
	 					setAttr("error", "account do not exist !");
	 				}
	 				else{
	 					setAttr("flag", 1);
	 					setAttr("error", "account or password is error!");
	 					//System.out.println("1##################");
	 				}
	 			}
	 			else{
	 				setAttr("flag", 0);
	 	 	 		setAttr("user", ry);
	 	 	 		setSessionAttr("userSession", ry);
	 	 	 		//SyslogController.WriteSysLog(SyslogController.DL, ry.getStr("user_id"),"sys_user",ip,ry);
					SyslogController.WriteSysLogDetail(SyslogController.DL, ry.getStr("user_id"),"sys_user",ip,ry,ry.getStr("user_name")+" sign in ");
	 			}
 			}
 			else{
 				setAttr("flag", 1);
 	 			setAttr("error", "barcode is incorrect!");
 			}
 		}
 		//加点调试的私货
		/*
		BlindboxMouldModel bm=new BlindboxMouldModel();
 		bm.set("goods_count",10);
		bm.set("goods_names","sdafsdf");
		List<modelinfoModel> list=BusinessUtils.Model2List(bm,"BlindboxMouldModel");
		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).fldName+"="+list.get(i).fldValue);
		}
		 */
		renderJson();
	}

	/*
	
	@Clear(LoginInterceptor.class)
	public void doyzmLogin(){
		String ip=(String)getSession().getAttribute("clientIP");
		String sjhm=getPara("sjhm").trim();
 		String yzm= getPara("yzm").trim();
 		String xtyhm= getPara("xtyhm").trim();
 		final HttpSession httpSession = getSession();
		String currentyzm = (String) httpSession.getAttribute(ConstantList.GL_DL_PASSWORD);
		
		String dlsql="select * from TB_XT_RYXXB where xtzh=?";
		SysuserModel ry=SysuserModel.dao.findFirst(dlsql,xtyhm);
		if(ry== null){
		   setAttr("flag", 1);
		   setAttr("error", "非授权用户，请与系统管理员联系");
		}
		else{
		   if(ry.getStr("LXDH").equals(sjhm)){
			   //if(yzm.equals(currentyzm)){
			   if(true){
				   setAttr("flag", 0);
	 	 	 	   setAttr("user", ry);
	 	 	 	   setSessionAttr("userSession", ry);
	 	 	 	   if(ry.getStr("GZJS").equals("9")){
		 	 		    //XtLogController.WriteSysLog(XtLogController.GLYDL, ry.getStr("YHRYBH"),"TB_XT_RYXXB",XtLogController.GLYDLBZ,ip,ry);
		 	 	   }
		 	 	   else{
		 	 		    //XtLogController.WriteSysLog(XtLogController.YBYHDL, ry.getStr("YHRYBH"),"TB_XT_RYXXB",XtLogController.YBYHDLBZ,ip,ry);
		 	 	   }
			   }
			   else{
				   setAttr("flag", 1);
				   setAttr("error", "手机短信验证码错误");
			   }
		   }
		   else{
			   setAttr("flag", 1);
			   setAttr("error", "手机号未注册，请与系统管理员联系");
		   }
		}	
 		renderJson();
	}
	*/
	
	public void doLogout(){
		String ip=(String)getSession().getAttribute("clientIP");
		SysuserModel ry = getSessionAttr("userSession");
		//SyslogController.WriteSysLog(SyslogController.TC, user.getStr("user_id"),"sys_user",ip,user);
		SyslogController.WriteSysLogDetail(SyslogController.DL, ry.getStr("user_id"),"sys_user",ip,ry,ry.getStr("user_name")+" sign out");
		getSession().removeAttribute("userSession");
		getSession().invalidate();
		redirect("/login");
	}
	
	public void updatePw(){
		Boolean flag = false;
		SysuserModel user = getSessionAttr("userSession");
		Md5Crypt md5=new Md5Crypt();
		String oldPw = getPara("oldPw").toUpperCase();//为防止字母的大小写，全改成大写，这样登录也不管大小写了
		String newPw = getPara("newPw").toUpperCase();
		String rybh = getPara("rybh");
		String password ="";
		String message = "";
		try {
			SysuserModel ry=SysuserModel.dao.findFirst("select * from sys_user where id=?",rybh);
			if(ry!=null){
				password = ry.getStr("password");
				if(password.equals(Md5Crypt.EncoderByMd5(oldPw))){
					flag = ry.set("password", Md5Crypt.EncoderByMd5(newPw)).update();
					message = "password has been revised successfully";
					String ip=(String)getSession().getAttribute("clientIP");
					//SyslogController.WriteSysLog(SyslogController.XG, user.getStr("user_id"),"sys_user",ip,user);
					SyslogController.WriteSysLogDetail(SyslogController.DL, ry.getStr("user_id"),"sys_user",ip,ry,ry.getStr("user_name")+" update password");
				}
				else{
					message = "old password is incorrect";
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("flag", flag);
		setAttr("message", message);
		renderJson();
	}
	/*
	public static void setPids(RyxxModel ryxx){
		if(ryxx.getStr("gid")!=null){
			String[] gids = ryxx.getStr("gid").split("#");
			String gidStr = "";
			String pids = "";
			String pidAttr="";
			Set<String> set = new HashSet<String>();
			for(int i=0;i<gids.length;i++){
				gidStr = gidStr + "'" +gids[i]+"',";
			}
			if(gidStr.endsWith(",")){
				gidStr = gidStr.substring(0, gidStr.length()-1);
			}
			String sqlPG = "select * from tb_xt_permissiongroup where gid in("+gidStr+")";
			List<PermissionGModel> list = PermissionGModel.dao.find(sqlPG);
			for(int i=0;i<list.size();i++){
				pids = pids + list.get(i).getStr("pid")+"#";
			}
			if(pids.endsWith("#")){
				pids = pids.substring(0, pids.length()-1);
			}
			String[] pidsArr = pids.split("#");
			for(int i=0;i<pidsArr.length;i++){
				set.add(pidsArr[i]);
			}
			for (String str : set) {  
				pidAttr = pidAttr + str + "#";
			}
			if(pidAttr.endsWith("#")){
				pidAttr = pidAttr.substring(0, pidAttr.length()-1);
			}
			ryxx.put("pid", pidAttr);
			System.out.println(pidAttr);
		}
	}

	public static String getPids(RyxxModel ryxx){
		String pidAttr="";
		if(ryxx.getStr("gid")!=null){
			String[] gids = ryxx.getStr("gid").split("#");
			String gidStr = "";
			String pids = "";
			Set<String> set = new HashSet<String>();
			for(int i=0;i<gids.length;i++){
				gidStr = gidStr + "'" +gids[i]+"',";
			}
			if(gidStr.endsWith(",")){
				gidStr = gidStr.substring(0, gidStr.length()-1);
			}
			String sqlPG = "select * from tb_xt_permissiongroup where gid in("+gidStr+")";
			List<PermissionGModel> list = PermissionGModel.dao.find(sqlPG);
			for(int i=0;i<list.size();i++){
				pids = pids + list.get(i).getStr("pid")+"#";
			}
			if(pids.endsWith("#")){
				pids = pids.substring(0, pids.length()-1);
			}
			String[] pidsArr = pids.split("#");
			for(int i=0;i<pidsArr.length;i++){
				set.add(pidsArr[i]);
			}
			for (String str : set) {  
				pidAttr = pidAttr + str + "#";
			}
			if(pidAttr.endsWith("#")){
				pidAttr = pidAttr.substring(0, pidAttr.length()-1);
			}
		}
		return pidAttr;
	}

	public void menuRoute(){
		RyxxModel user = getSessionAttr("userSession");
		String pids = user.getStr("pid");
		if(pids.indexOf('5')>-1){//发布审核
			redirect("/xxsh/publicXxsh");
		}else if(pids.indexOf('6')>-1){//发布审批
			redirect("/xxsh/tosh");
		}else if(pids.indexOf('1')>-1 || pids.indexOf('2')>-1){//发布信息 or 审批(审核)退回
			redirect("/xxfb/tolrlist");
		}else if(pids.indexOf('4')>-1 || pids.indexOf('6')>-1){//已发布内容维护
			redirect("/xxqs/toEditfb");
		}else if(pids.indexOf('4')>-1){//版面内容管理（系统管理 and 省厅单位）
			redirect("/img/workcolumn");
		}else if(pids.indexOf('4')>-1){//权限组管理
			redirect("/group");
		}else  if(pids.indexOf('4')>-1){//用户管理
			redirect("/user");
		}else if(pids.indexOf('8')>-1){//统计
			redirect("/visitsTj");
		}
	}
	*/

	/**
	 * 获取登录动态密码
	 */
	/*
	@Clear(LoginInterceptor.class)
	public void getDtsjPwd(){
		String sjhm=getPara("sjhm");
		int yxq=Integer.parseInt(getPara("yxq"));//这个有效期数字表示分钟
		final HttpSession httpSession = getSession();
		String oldYzm = (String) httpSession.getAttribute(Constant.GL_DL_PASSWORD);
		String result="";
		if (!StrKit.isBlank(oldYzm)) {
		   httpSession.removeAttribute(Constant.GL_DL_PASSWORD);
		}
		String dtPassword = SmsDynPwd.getRandNum(6);
		try{
			result = SmsDynPwd.sendDtPassword(sjhm,dtPassword);
			//先离线测试
			//result="00";
		}
	   catch(Exception e){
		   result="ee";
		   System.out.println(e.getMessage());
		   e.printStackTrace();
	   }
	   //发送成功
       if ("00".equals(result)) {
		  //记录操作日志
		  String ip = IPAddress.getLocalIp(getRequest());
		  XtLogController.WriteSysLog(XtLogController.HQDXYZM,"","",XtLogController.HQDXYZMBZ,ip,sjhm,sjhm);
		  httpSession.setAttribute(Constant.GL_DL_PASSWORD, dtPassword);
		  //设置有效期
		  final Timer timer = new Timer();
		  timer.schedule(new TimerTask() {
		    @Override
		    public void run() {
			  httpSession.removeAttribute(Constant.GL_DL_PASSWORD);
			  System.out.println("文物安全形势查看登录dtPassword删除成功");
			  timer.cancel();
		    }
		  }, yxq*60*1000);
	   }
	   else{
		  result = "ee";//验证码发送失败
	   }
	   //System.out.println("############################");
	   //System.out.println("pwd="+(String) httpSession.getAttribute(Constant.GL_DL_PASSWORD));
	   //System.out.println("result="+result);
	   //System.out.println("############################");
       setAttr("flag", result);
	   renderJson();
	}
	*/
}
