<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>user manager</title>
</head>
<body>
    <div id="wrapper">
        <%@ include file="../common/head.jsp"%>
        <div class="navbar-default navbar-side" role="navigation">
            <%@ include file="../common/menu.jsp"%>
        </div>
        <div id="page-wrapper">
            <div class="pageheader"><lan set-lan="html:menu.user">系统用户管理</lan><i class="fa fa-caret-down ml-xs"></i></div>
            <div class="page-inner">
                   <div class="hhBox">
                    <span class="wwmc"><lan set-lan="html:usermgr.unit">单位部门</lan>：</span>
                    <input class="form-control " type="text" id="cxyhdw" style="width: 150px;display: inline-block">
                   </div>
                   <div class="hhBox">
                        <span class="wwmc"><lan set-lan="html:actor.name">姓名</lan>：</span>
                        <input class="form-control " type="text" id="cxyhxm" style="width: 150px;display: inline-block">
                    </div>
                    <div class="hhBox">
                      <button type="button" class="btn btn-sm btn-success" onclick="qryYh()"><lan set-lan="html:movielist.query">查询</lan></button>
                        <button type="button" class="btn btn-sm btn-success" onclick="clearTj()"><lan set-lan="html:movielist.clear">清空</lan></button>
                      <button type="button" class="btn btn-sm btn-info"  id="waibao"><lan set-lan="html:button.new">新增</lan></button></center>
                    </div>
                    <div class="clear"></div>
            </div>
            <div class="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="infoTable">
                                <thead>
                                <tr>
                                    <th style="width: 45px"><lan set-lan="html:movielist.order">序号</lan></th>
                                    <th style="width: 120px"><lan set-lan="html:usermgr.account">账号</lan></th>
                                    <th style="width: 160px"><lan set-lan="html:actor.name">姓名</lan></th>
                                    <th style="width: 160px"><lan set-lan="html:usermgr.phone">联系方式</lan></th>
                                    <th style="width: 100px;"><lan set-lan="html:usermgr.role">客服角色</lan></th>
                                    <th style="width: 160px"><lan set-lan="html:usermgr.usernum">服务人数</lan></th>
                                    <th ><lan set-lan="html:usermgr.jurisdiction">权限</lan></th>
                                    <th style="width: 160px"><lan set-lan="html:usermgr.registration.time">登记时间</lan></th>
                                    <th style="width: 300px"><lan set-lan="html:movielist.operation">操作</lan></th>
                                </tr>
                                </thead>
                                <tbody id="items_body">
                                
                                </tbody>
                            </table>
                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal zhezhao2" style="display: none">
        <div class="modal-dialog" style="width:360px;margin: 8% auto;">
            <div class="modal-content">
                <div class="modal-header state modal-success">
                    <button type="button" class="close close1"><span>&times;</span></button>
                    <h4 class="modal-title" id="divbt"><lan set-lan="html:usermgr.new.user">新增用户</lan></h4>
                </div>
                <div class="modal-body">
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:usermgr.unit">单位</lan>：</dt>
                            <dd>
                                <input class="form-control" type="text" style="width: 200px" id="yhdw">
                                <input type="hidden" id="userid">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:usermgr.account">账号</lan>：</dt>
                            <dd>
                                <input class="form-control" type="text" placeholder="用户名" style="width: 200px" id="yhzh"  maxlength="18">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:actor.name">姓名</lan>：</dt>
                            <dd>
                                <input class="form-control" type="text" placeholder="用户姓名" style="width: 200px" id="yhxm"  maxlength="7">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:usermgr.sex">性别</lan>：</dt>
                            <dd>
                                <lan set-lan="select:usermgr.sextype">
                                <select class="form-control" style="width: 200px" id="sextype">
                                </select></lan>
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:usermgr.phone">联系方式</lan>：</dt>
                            <dd>
                                <input class="form-control" type="text" placeholder="手机号" style="width: 200px" id="yhsjh"  maxlength="11">
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:usermgr.role">客服角色</lan>：</dt>
                            <dd>
                                <lan set-lan="select:usermgr.roletype">
                                    <select id="roletype" style="width: 200px">
                                    </select></lan>
                            </dd>
                        </dl>
                    </div>
                    <div class="ypbglist100">
                        <dl>
                            <dt class="color-danger"><lan set-lan="html:usermgr.jurisdiction">权限</lan>：</dt>
                            <dd>
<%--                                <input type="checkbox" name="chkbox" value="G01" /><lan set-lan="html:menu.inputmovie">发布电影</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G02" /><lan set-lan="html:menu.rule.setting">规则设置</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G06" /><lan set-lan="html:menu.adv">轮播广告设置</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G07" /><lan set-lan="html:msginput.system.message">系统消息设置</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G18" /><lan set-lan="html:msginput.system.article">信息贴文</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G19" /><lan set-lan="html:msginput.system.protocol">协议</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G08" /><lan set-lan="html:menu.shop">店铺账号审核</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G15" /><lan set-lan="html:menu.ratio">店铺比率调整</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G14" /><lan set-lan="html:menu.goods">商品上架审核</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G16" /><lan set-lan="html:menu.blindbox">盲盒设置</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G17" /><lan set-lan="html:menu.coupon">优惠券设置</lan><br>--%>
<%--                                <input type="checkbox" name="chkbox" value="G13" /><lan set-lan="html:menu.stat">信息综合统计</lan><br>--%>
                                <input type="checkbox" name="chkbox" value="G10" /><lan set-lan="html:menu.user">系统用户管理</lan><br>
                                <input type="checkbox" name="chkbox" value="G11" /><lan set-lan="html:menu.dictionary">数据字典维护</lan><br>
                                <input type="checkbox" name="chkbox" value="G12" /><lan set-lan="html:menu.syslog">系统日志</lan><br>
                                <input type="checkbox" name="chkbox" value="G5" />平台地址管理<br>
                                <input type="checkbox" name="chkbox" value="G20" />平台地址管理<br>
                                <input type="checkbox" name="chkbox" value="G21" />比赛地址GAS查询

                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type=hidden id="yhbh">
                    <button type="button" class="btn btn-success" onclick="SaveYhxx()"><lan set-lan="html:message.sureText">确定</lan></button>
                    <button type="button" class="btn btn-default" onclick="closeYhck()"><lan set-lan="html:message.cancelText">取消</lan></button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal zhezhao3" style="display: none">
        <div class="modal-dialog" style="width:700px;margin: 8% auto;">
            <div class="modal-content">
                <div class="modal-header state modal-success">
                    <button type="button" class="close close1"><span>&times;</span></button>
                    <h4 class="modal-title" id="divbt1"><lan set-lan="html:modpwd.setuserserve">服务人员设置</lan></h4>
                </div>
                <div class="modal-body">
                    <div class="page-inner">
                        <div class="hhBox">
                            <span class="wwmc"><lan set-lan="html:usermgr.phone">联系方式</lan>：</span>
                            <input class="form-control " type="text" id="user_cxyhdw" style="width: 150px;display: inline-block">
                        </div>
                        <div class="hhBox">
                            <span class="wwmc"><lan set-lan="html:actor.name">姓名</lan>：</span>
                            <input class="form-control " type="text" id="user_cxyhxm" style="width: 150px;display: inline-block">
                        </div>
                        <div class="hhBox">
                            <button type="button" class="btn btn-sm btn-success" onclick="qryYh1()"><lan set-lan="html:movielist.query">查询</lan></button>
                            <button type="button" class="btn btn-sm btn-success" onclick="clearTj1()"><lan set-lan="html:movielist.clear">清空</lan></button>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <table class="table table-striped table-bordered table-hover" id="infoTable1">
                        <thead>
                        <tr>
                            <th style="width: 45px"><lan set-lan="html:movielist.order">序号</lan></th>
                            <th style="width: 120px"><lan set-lan="html:usermgr.phone">联系方式</lan></th>
                            <th style="width: 120px"><lan set-lan="html:actor.name">姓名</lan></th>
                            <th style="width: 120px"><lan set-lan="html:actor.service">服务人员</lan></th>
                            <th style="width: 300px"><lan set-lan="html:movielist.operation">操作</lan></th>
                        </tr>
                        </thead>
                        <tbody id="items_body1">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../common/modpwd.jsp"%>
    <script>
        initlan();
        var vlan = $.tmCookie.getCookie('lan');
        var $thisTable = $("#infoTable");
        initTable("infoTable");
        var $thisTable1 = $("#infoTable1");
        initTable("infoTable1");
        $(function(){

            $(".xiugmm").on('click',function(){
                $(".zhezhao1").show();
            });
            $(".close2").on('click',function(){
                $(".zhezhao1").hide();
            });
            $("#page-wrapper").height($(window).height()-$(".navbar").height()-20);
            $("#waibao").on('click',function(){
                $(".zhezhao2").show();
                $("#divbt").html(get_lan("usermgr.new.user"));
                $("#yhdw").val("");
     		    $("#yhzh").val("");
     		    $("#yhxm").val("");
     		    $("#sextype").val("");
                $("#yhsjh").val("");
                $("#userid").val("");
                $("#roletype").val("");
                var obj = document.getElementsByName("chkbox");
                for(var i=0;i<6;i++){
                    obj[i].checked=false;
                }
            });
            $(".close1").on('click',function(){
                $(".zhezhao2").hide();
                $(".zhezhao3").hide();
            });
        	qryYh();
        });
        function qryYh1(){
            setUserServe();
        }
        function closeYhck(){
           $(".zhezhao2").hide();
        }
        
        var searchTimer = null;
        var status = false;
        function qryYh(){
        	var yhxm=$("#cxyhxm").val();
            var yhdw=$("#cxyhdw").val();
        	clearTimeout(searchTimer);
        	var sexlist=[];
        	var rolelist=[];
            switch (vlan) {
                case 'cn':
                    sexlist=cn["usermgr.sextype"];
                    rolelist=cn["usermgr.roletype"];
                    break;
                case 'en':
                    sexlist=en["usermgr.sextype"];
                    rolelist=en["usermgr.roletype"];
                    break;
                default:
                    sexlist=en["usermgr.sextype"];
                    rolelist=en["usermgr.roletype"];
            }
        	searchTimer = setTimeout(function(){
        		$.ajax({
            		type:"post",
            		url:"<%=ctxPath%>/sysset/qryYh",
            		beforeSend:function(){},
            		data:{"yhxm":yhxm,"yhdw":yhdw},
            		success:function(data){
            			var t = $thisTable.DataTable();
    					t.rows("tr").remove().draw(false);
    					var tableRowsArray = [];
            			var list = data.yhlist;
            			for(var i=0;i<list.length;i++){
                            var btnstr="<button type=\"button\" class=\"btn btn-xs btn-info mr-xs\" onclick='initMm(this)' data-jlbh='"+list[i].user_id+"'>"+get_lan("modpwd.initialization")+"</button>";
                            btnstr=btnstr+"<button type=\"button\" class=\"btn btn-xs btn-warning mr-xs\" onclick='updYh(this)' data-jlbh='"+list[i].user_id+"'>"+get_lan("button.modify")+"</button>";
                            btnstr=btnstr+"<button type=\"button\" class=\"btn btn-xs btn-danger\" onclick='delYh(this)' data-jlbh='"+list[i].user_id+"'>"+get_lan("button.delete")+"</button>";
                            var idx=parseInt(list[i].type_flg);
                            if(idx==2){
                                btnstr +="<button type=\"button\" class=\"btn btn-xs btn-info mr-xs\" onclick='setUserServe(this)' data-jlbh='"+list[i].user_id+"'>"+get_lan("modpwd.setuserserve")+"</button>"
                            }
                            var roledesc="";
                            if(idx>0){
                                roledesc="<span style='color:red;'>"+rolelist[idx].name+"</span>";
                            }
                            else{
                                roledesc=rolelist[idx].name;
                            }
                            /*
            		        for(var j=0;i<rolelist.length;j++){
                                var typeflg=list[i].type_flg+"";
                                if(rolelist[j].id==typeflg){
                                    roledesc=rolelist[j].name;
                                    break;
                                }
                            }
                             */
                            tableRowsArray = [
    						   i+1,
                               list[i].account,
    						   list[i].user_name,
                               list[i].user_phone,
                                roledesc,
                                idx==2?list[i].user_num:"-",
                                list[i].rightcontent,
    						   $.tmDate._toString(list[i].create_time,'yyyy-MM-dd HH:mm'),
    						   btnstr			    
    						];
                            // console.log(tableRowsArray);
    						//t.row.add(tableRowsArray).draw();
            		        var tr=t.row.add(tableRowsArray).draw().node();
            		        $(tr).find("td:eq(2)").addClass('t-rwmc');
            			}
            		}
            	});
        	}, 200);
        }
        //找权限值对应的控件索引
        function getCheckboxIdx(value){
            switch(value)
            {
                // case "G01":
                //     return 0;
                // case "G02":
                //     return 1;
                // case "G06":
                //     return 2;
                // case "G07":
                //     return 3;
                // case "G18":
                //     return 4;
                // case "G19":
                //     return 5;
                // case "G08":
                //     return 6;
                // case "G15":
                //     return 7;
                // case "G14":
                //     return 8;
                // case "G16":
                //     return 9;
                // case "G17":
                //     return 10;
                // case "G13":
                //     return 11;
                case "G10":
                    return 0;
                case "G11":
                    return 1;
                case "G12":
                    return 2;
                case "G5":
                    return 3;
                case "G20":
                    return 4;
                case "G21":
                    return 5;
                default:
                    return 0;
            }
        }

        
        function delYh(obj){
        	var userid = $(obj).data("jlbh");
        	yyConfirm({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),cancelText:get_lan("message.cancelText"),flag:"1",style:"q",content:get_lan("message.delete.content"),callback:function(ok){
        		if(ok){
        			$.ajax({
                		type:"post",
                		url:"<%=ctxPath%>/sysset/delYh",
                		data:{"userid":userid},
                		success:function(data){
                			if(data == true){
                				document.location.reload(); 
                			}
                			else{
                				yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("message.delete.fail")});
                			}
                		}
                	});
        		}
        	}});
        }
        
        
        var waitTimer = null;
        function updYh(obj){
            var userid = $(obj).data("jlbh");
        	$.ajax({
        		type:"post",
        		url:"<%=ctxPath%>/sysset/updYh",
        		data:{"userid":userid},
        		success:function(data){
        		   $("#userid").val(userid);
        		   $("#yhxm").val(data.yhxx.user_name);
        		   $("#yhzh").val(data.yhxx.account);
        		   $("#sextype").val(data.yhxx.user_sex);
        		   $("#yhdw").val(data.yhxx.user_unit);
        		   $("#yhsjh").val(data.yhxx.user_phone);
        		   $("#divbt").html(get_lan("usermgr.update.user"));
                    $("#roletype").val(data.yhxx.type_flg);
        		   var yhqx=data.yhxx.privilege;
        		   //先清空
                    var obj = document.getElementsByName("chkbox");
                   for(var i=1;i<=5;i++){
                        //$("#checkbox"+i).checked=false;
                       obj[i-1].checked=false;
                   }
                   //然后赋值
                   if(yhqx!=null) {
                       if (yhqx.indexOf("#") == -1) {
                           if (yhqx != "") {
                               //$("#checkbox" + parseInt(yhqx.substr(1, yhqx.length))).checked = true;
                               //obj[parseInt(yhqx.substr(1, yhqx.length))].checked = true;
                               obj[getCheckboxIdx(yhqx)].checked = true;
                           }
                       } else {
                           var yhqxarray = yhqx.split("#");
                           for (var i = 0; i < yhqxarray.length; i++) {
                               if (yhqxarray[i] != "") {
                                   //$("#checkbox" + parseInt(yhqxarray[i].substr(1, yhqxarray[i].length))).checked = true;
                                   //var idx = yhqxarray[i].substr(1, yhqxarray[i].length);
                                   //alert(idx);
                                   //obj[parseInt(idx)-1].checked = true;
                                   obj[getCheckboxIdx(yhqxarray[i])].checked = true;
                               }
                           }
                       }
                   }
        		   $(".zhezhao2").show();
        		}
            });
        }

        function setUserServe(obj){
            if(obj){
                var userid = $(obj).data("jlbh");
            }else{
                userid=qjuser_id;
            }
            var yhxm=$("#user_cxyhxm").val();
            var yhdw=$("#user_cxyhdw").val();
            qjuser_id=userid;
            clearTimeout(searchTimer);
            var sexlist=[];
            var rolelist=[];
            switch (vlan) {
                case 'cn':
                    sexlist=cn["usermgr.sextype"];
                    rolelist=cn["usermgr.roletype"];
                    break;
                case 'en':
                    sexlist=en["usermgr.sextype"];
                    rolelist=en["usermgr.roletype"];
                    break;
                default:
                    sexlist=en["usermgr.sextype"];
                    rolelist=en["usermgr.roletype"];
            }
            searchTimer = setTimeout(function(){
                $.ajax({
                    type:"post",
                    url:"<%=ctxPath%>/sysset/userServe",
                    beforeSend:function(){},
                    data:{"customer_service_id":userid,yhxm:yhxm,yhdw:yhdw},
                    success:function(data){
                        var t = $thisTable1.DataTable();
                        t.rows("tr").remove().draw(false);
                        var tableRowsArray = [];
                        var list = data.userModels;
                        // console.log(data)
                        for(var i=0;i<list.length;i++){
                            var btnstr="<button type=\"button\" class=\"btn btn-xs btn-info mr-xs\" onclick='relevance(this)' data-jlbh='"+list[i].user_id+"'>"+get_lan("modpwd.relevance")+"</button>";
                            if(list[i].customer_service_id){
                                btnstr="<button type=\"button\" class=\"btn btn-xs btn-info mr-xs\" onclick='setrelevance(this)' data-jlbh='"+list[i].user_id+"'>"+get_lan("modpwd.setrelevance")+"</button>";
                            }
                            var customer_service_type=parseInt(list[i].customer_service_type);
                            if(customer_service_type==0 && (list[i].customer_service_id!="" && list[i].customer_service_id!=null)){
                                btnstr ="-";
                            }
                            tableRowsArray = [
                                i+1,
                                list[i].user_phone,
                                list[i].user_name,
                                list[i].customer_service_name,
                                btnstr
                            ];
                            // console.log(tableRowsArray);
                            var tr=t.row.add(tableRowsArray).draw().node();
                            $(tr).find("td:eq(2)").addClass('t-rwmc');
                        }
                    }
                })
            },200)
            $(".zhezhao3").show();
        }
        function relevance(obj){
            var  userid= $(obj).data("jlbh");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/sysset/setuserservice",
                data:{user_id:userid,service_id:qjuser_id},
                success:function(data){
                    // document.location.reload();
                    setUserServe();
                }
            });
        }
        function setrelevance(obj){
            var  userid= $(obj).data("jlbh");
            $.ajax({
                type:"post",
                url:"<%=ctxPath%>/sysset/deluserservice",
                data:{user_id:userid},
                success:function(data){
                    // document.location.reload();
                    setUserServe();
                }
            });
        }
        function initMm(obj){
        	var  userid= $(obj).data("jlbh");
        	$.ajax({
        		type:"post",
        		url:"<%=ctxPath%>/sysset/initMm",
        		data:{"userid":userid},
        		success:function(data){
        			if(data.flag==true){
        			   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("modpwd.initialization.value")});
        			}
        			else{
        			   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("error.initialization.password")});
        			}
        		}
            });
        }
        
        function clearTj(){
        	$("#cxyhxm").val("");
        	$("#cxyhddw").val("");
        }
        function clearTj1(){
            $("#user_cxyhxm").val("");
            $("#user_cxyhddw").val("");
        }
        	
        
        function SaveYhxx(){
           var yhdw=$("#yhdw").val();
 		   var yhzh=$("#yhzh").val();
 		   var yhxm=$("#yhxm").val();
 		   var yhsjh=$("#yhsjh").val();
 		   var userid=$("#userid").val();
 		   var yhxb=$("#sextype").val();
            var kfjs=$("#roletype").val();
 		   var yhqx="";
 		   var obj = document.getElementsByName("chkbox");
           for(var i=0;i<obj.length;i++){
               if(obj[i].checked) {
                   yhqx += obj[i].value + "#";
               }
           }
           if(yhqx!=""){
               yhqx=yhqx.substr(0,yhqx.length-1);
           }
 		   if(yhdw==""){
 			   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("usermgr.unit")+get_lan("message.blank")});
 			   return;
 		   }
            if(kfjs==""){
                yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("usermgr.role")+get_lan("message.blank")});
                return;
            }
 		   if(yhzh==""){
 			   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("usermgr.account")+get_lan("message.blank")});
 			   return;
 		   }
 		   if(yhxm==""){
 			   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("actor.name")+get_lan("message.blank")});
 			   return;
 		   }
 		   if(yhsjh==""){
 			   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("usermgr.phone")+get_lan("message.blank")});
 			   return;
 		   }
 		   if(yhxb==""){
			   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("usermgr.sex")+get_lan("message.blank")});
			   return;
		   }
 		   if(yhqx==""){
			   yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),content:get_lan("usermgr.jurisdiction")+get_lan("message.blank")});
			   return;
		   }
 		   //alert(yhqx);
 		   $.ajax({
      		  type:"post",
      		  url:"<%=ctxPath%>/sysset/saveYh",
      		  data:{"userid":userid,"yhdw":yhdw,"yhzh":yhzh,"yhsjh":yhsjh,"yhxm":yhxm,"yhqx":yhqx, "yhxb":yhxb,"kfjs":kfjs,"ctxPath":"<%=ctxPath%>"},
      		  success:function(data){
      			  if(data.flag == true){
      				 closeYhck();
        			 document.location.reload();    
    			  }
    			  else{
    				 if(data.errmsg!=''){
    				     yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:data.errmsg});
    				 }
    				 else{
    					 yyAlert({title:get_lan("message.prompttitle"),sureText:get_lan("message.sureText"),style:"e",content:get_lan("error.save.user")});
    				 }
    			  }
      			  
      		  }
           });
        }
        
    </script>
</body>
</html>
