<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
     <img src="<%=ctxPath%>/assets/img/betswap.png" alt="" class="logoImg" style="width: 70px" />
     <div class="navbar-header">BETSWAP</div>
     <div class="topright">
          <div class="right_top"><lan set-lan="html:head.welcome">欢迎您，</lan>${user.user_name}
               <a class="xiugmm" href="javascript:;"><lan set-lan="html:head.editpwd">修改密码</lan></a>
               <a href="javascript:;" onclick="loginout()"><lan set-lan="html:head.out">退出</lan></a>
     </div>
     </div>
</div>
<script type="text/javascript">
function loginout(){
	yyConfirm({flag:"1",style:"q",content:get_lan("head.outprop"),cancelText:get_lan("message.cancelText"),sureText:get_lan("message.sureText"),callback:function(ok){
		if(ok){
			window.location.href = "<%=ctxPath%>/login/doLogout";
		}
	}});
}
</script>