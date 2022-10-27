<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>login</title>
    <link href="<%=ctxPath%>/assets/css/bootstrap.css" rel="stylesheet" />
    <link href="<%=ctxPath%>/assets/css/font-awesome.css" rel="stylesheet" />
    <link href="<%=ctxPath%>/js/alert/dialog-two.css" rel="stylesheet" type="text/css">
    <link href="<%=ctxPath%>/assets/css/basic.css" rel="stylesheet" />
   	<link href="<%=ctxPath%>/assets/img/favicon.ico" type="image/x-icon" rel="shortcut icon" />
</head>
<style>
    body{
        background-image:url("<%=ctxPath%>/assets/img/bj.jpg");
        background-size: cover;
        background-attachment: fixed;
    }
    
</style>
<body>
    <div class="container">
        <div class="text-center">
            <div class="titledl">
                <p><img src="<%=ctxPath%>/assets/img/betswap.png" width="91px" height="91px" /></p>
                <lan set-lan="html:login.sysname">BETSWAP管理平台</lan>
            </div>
        </div>
        <div class="loginBox"> 
            <div class="panel-body paddingall">
                    <div class="yhmBox">
                        <span class="dlyhm"><lan set-lan="html:login.username">用户名</lan></span>
                        <div class="form-group input-group ybWidth">
                            <span class="input-group-addon sbWidth"><i class="fa fa-tag"  ></i></span>
                            <input id="xtzh" type="text" class="form-control sqHeight"  />
                        </div>
                    </div>
                    <div class="yhmBox">
                        <span class="dlyhm"><lan set-lan="html:login.password">密&nbsp;码</lan></span>
                        <div class="form-group input-group ybWidth">
                            <span class="input-group-addon sbWidth"><i class="fa fa-lock"  ></i></span>
                            <input id="xtmm" type="password" class="form-control sqHeight"   />
                        </div>
                    </div>
                    <div class="yhmBox">
                        <span class="dlyhm"><lan set-lan="html:login.barcode">验证码</lan></span>
                        <div class="form-group input-group ybWidth">
                            <span class="input-group-addon sbWidth"><i class="fa fa-road"  ></i></span>
                            <input id="sjyzm" type="text" class="form-control sqHeight"  style="width:120px;" />
                            <img alt="验证码" src="<%=ctxPath%>/img/imgCode.jpg" title="点击切换验证码" width="80px" height="36px" 
							id="codeimg" onclick="this.src='<%=ctxPath%>/login/imgCode?'+new Date().getTime()+'&'+Math.random()"/>
                        </div>
                    </div>

<%--                   <div class="yhmBox">--%>
<%--                    <span class="dlyhm"><lan set-lan="html:login.lan">语言</lan></span>--%>
<%--                    <div class="form-group input-group ybWidth">--%>
<%--                        <span class="input-group-addon sbWidth"><i class="fa fa-inbox" ></i></span>--%>
<%--                        <select class="form-control sqHeight" onchange="changelan()" id="sellan">--%>
<%--                            <option value="cn">简体中文</option>--%>
<%--                            <option value="en">english</option>--%>
<%--                            <option value="in">IndonesiaName</option>--%>
<%--                        </select>--%>
<%--                    </div>--%>
<%--                    </div>--%>

                    <div class="yhmBox center">
                        <a class="btn btn-primary dengl" onclick="login()">
                            <lan set-lan="html:login.action">登录</lan></a>
                    </div>
            </div>
        </div>  
        <div class="footer">
            <p><lan set-lan="html:login.bottom">版权所有</lan></p>
        </div>
    </div>
</body>
<script type="text/javascript">
    var vlan = $.tmCookie.getCookie('lan');
    if(vlan==null||vlan=="") {
        $.tmCookie.setCookie("lan", "cn", 1000 * 60 * 60);
        vlan="cn";
    }
    $("#sellan").val(vlan);
    initlan();

$(function() {
   $("#xtzh").on("keydown", function (e) {
      if (e.keyCode == 13) {
    	  setTimeout(function(){
              $("#xtmm").focus();
          },50)
      }
   });
   $("#xtmm").on("keydown", function (e) {
	      if (e.keyCode == 13) {
	    	  setTimeout(function(){
	    		  login();
	          },50)
	      }
   });
   //进来后首先切换下验证码
   $("#codeimg").click();
});

function login(){
	var xtzh=trim(document.getElementById("xtzh").value);
	var xtmm=trim(document.getElementById("xtmm").value);
	var imgcode = trim(document.getElementById("sjyzm").value);
	//alert("111");
	if(xtzh.length==0){
        //alert("111-1");
		yyAlert({title:get_lan("message.prompttitle"),content:get_lan("login.message.blankusername"),sureText:get_lan("message.sureText")});
		$("#xtzh").focus();
		return;
	}
    //alert("222");
	if(xtmm.length==0){
        //alert("222-1");
		yyAlert({title:get_lan("message.prompttitle"),content:get_lan("login.message.blankpassword"),sureText:get_lan("message.sureText")});
		$("#xtmm").focus();
		return;
	}
    //alert("333");
	if(isEmpty(imgcode)){
		yyAlert({title:get_lan("message.prompttitle"),content:get_lan("login.message.blankbarcode"),sureText:get_lan("message.sureText")});
		$("#sjyzm").focus();
		return;
	}
    //alert("444");
	$.ajax({
        url:"<%=ctxPath%>/login/doLogin",
        type:"post",
        data:{
            "xtzh" : xtzh,
            "xtmm" : xtmm,
            "imgcode":imgcode
        },
        success:function(data) {
        	if(data.flag=="0"){
        		window.location.href = "<%=ctxPath%>/system/syspara";
        	}
        	else{
        		yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:data.error});
        	}
        }
    });
    //alert("555");
}

function changelan(){
    var  newlan=$("#sellan").val();
    //alert(newlan);
    $.tmCookie.setCookie("lan",newlan,1000 * 60 * 60);
    initlan();
}
</script>
</html>
