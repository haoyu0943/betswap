<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@taglib uri="/WEB-INF/yy.tld" prefix="yy" %>
<%

	String path = request.getContextPath();
	int port = request.getServerPort();
	String ctxPath = "";
	if(port==80){
		 ctxPath = request.getScheme()+"://"+request.getServerName()+path;
	}else{
		 ctxPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	}
	request.setAttribute("ctxPath", ctxPath);
	request.setAttribute("contextPath", request.getContextPath());
	//生成随机数，方便引用
	String[] chars = {"0","1","2","3","4","5","6","7","8",
	          "9","A","B","C","D","E","F","G","H",
	          "I","J","K","L","M","N","O","P","Q",
	          "R","S","T","U","V","W","X","Y","Z"};
    String  res="";
    for(int i = 0; i < 6 ; i ++) {
       int id = (int)Math.ceil(Math.random()*35);
       res += chars[id];
    }
    request.setAttribute("randomNum", res);
	String serviceUrl="http://8.142.45.106:8080/api/v1";
//	String serviceUrl="http://192.168.2.242:8080/api/v1";
	request.setAttribute("serviceUrl", serviceUrl);
%>
