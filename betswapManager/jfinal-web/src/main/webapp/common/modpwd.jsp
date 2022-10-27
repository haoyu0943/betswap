<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="modal zhezhao1" style="display: none" id="upw">
        <div class="modal-dialog" style="width: 400px">
            <div class="modal-content">
                <div class="modal-header state modal-success">
                    <button type="button" class="close close2"><span>&times;</span></button>
                    <h4 class="modal-title"><lan set-lan="html:modpwd.title">修改密码</lan></h4>
                </div>
                <div class="modal-body">
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:modpwd.oldpwd">原始密码：</lan></dt>
                            <dd>
                                <input class="form-control " type="text" id="oldPw">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:modpwd.newpwd">新密码：</lan></dt>
                            <dd>
                                <input class="form-control " type="text" id="newPw">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:modpwd.confirmpwd">确认密码：</lan></dt>
                            <dd>
                                <input class="form-control " type="text" id="agnewPw">
                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="srybh" value="${user.id}">
                    <button type="button" class="btn btn-success" onclick="modpwd()"><lan set-lan="html:message.sureText">确定</lan></button>
                    <button type="button" class="btn btn-default" onclick="cancel()"><lan set-lan="html:message.cancelText">取消</lan></button>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    function modpwd(){
    	var oldPw = $("#oldPw").val();
    	var newPw = $("#newPw").val();
    	var agnewPw = $("#agnewPw").val();
    	var rybh = $("#srybh").val();
    	if(isEmpty(oldPw)){
    		//$("#upw").find(".message").stop().show().html("原密码不能为空").fadeOut(4000);
    		yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blankoldpwd"),sureText:get_lan("message.sureText")});
    		$("#oldPw").select();
    		return;
    	}
    	
    	if(isEmpty(newPw)){
    		//$("#upw").find(".message").stop().show().html("新密码不能为空！！").fadeOut(4000);
    		yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.blanknewpwd"),sureText:get_lan("message.sureText")});
    		$("#newPw").select();
    		return;
    	}
    	
    	if(newPw == oldPw){
    		//$("#upw").find(".message").stop().show().html("新密码和原密码不能一致！！").fadeOut(4000);
    		yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.canteqold"),sureText:get_lan("message.sureText")});
    		$("#newPw").select();
    		return;
    	}
    	
    	if(newPw != agnewPw){
    		//$("#upw").find(".message").stop().show().html("两次密码输入不一致！！").fadeOut(4000);
    		yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.canteqnew"),sureText:get_lan("message.sureText")});
    		$("#agnewPw").select();
    		return;
    	}
    	//alert(rybh);
    	$.ajax({
    		type:"post",
    		url:"<%=ctxPath%>/login/updatePw",
    		beforeSend:function(){},
    		data:{"oldPw":oldPw,"newPw":newPw,"rybh":rybh},
    		success:function(data){
    			if(data.flag == true){
    				$("#oldPw").html("");
    		    	$("#newPw").html("");
    		    	$("#agnewPw").html("");
    		    	yyAlert({title:get_lan("message.prompttitle"),content:get_lan("modpwd.sucess"),sureText:get_lan("message.sureText")});
    		    	$("#upw").hide();
    			}
    			else{
    				//$("#upw").find(".message").stop().show().html(data.message).fadeOut(4000);
    				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:data.message});
    				$("#oldPw").select();
    			}
    		}
    	});
    }
    
    function cancel(){
    	$("#upw").hide();
    	$("#oldPw").html("");
    	$("#newPw").html("");
    	$("#agnewPw").html("");
    }
    </script>
   <c:if test="${user.type_flg!='0'}">
    <%@ include file="../message/message.jsp"%>
   </c:if>